package com.yunshao.score;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yunshao
 * @Date: 2018/8/15 15:58
 */
@RestController
@RequestMapping
public class ViewController {
    /**
     * @param x1xishu       严重报警系数变量
     * @param x2xishu       中级报警系数变量
     * @param x3xishu       一般报警系数变量
     * @param genghuancishu 更换驾驶员次数
     * @param yanzhongcishu 严重报警次数
     * @param zhongjicishu  中级报警次数
     * @param yibancishu    一般报警次数
     * @param xingcheng     总行程(KM)
     * @return
     */
    @RequestMapping(value = "/getScore")
    public String getScore(int x1xishu, int x2xishu, int x3xishu, int x1fenmu, int x2fenmu, int x3fenmu, int genghuancishu, int yanzhongcishu, int zhongjicishu, int yibancishu, double xingcheng) {
        Map map = new HashMap();
        //日行程小于10km为无效行程，视同为当日无行程，不予打分；
        if (xingcheng < 10) {
            map.put("genghuanscore", 0);
            map.put("yanzhongscore", 0);
            map.put("zhongjiscore", 0);
            map.put("yibanscore", 0);
            map.put("zongscore", 100);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return jsonObject.toString();
        }
        /**
         * 这里先处理 x 的系数  也可以 封装成一个方法
         */
        int myX1 = x1xishu * yanzhongcishu;
        int myX2 = x2xishu * zhongjicishu;
        int myX3 = x3xishu * yibancishu;
        //得分计算
        String s = myScore(myX1, myX2, myX3, genghuancishu, xingcheng, yanzhongcishu, zhongjicishu, yibancishu, x1fenmu, x2fenmu, x3fenmu);
        return s;
    }

    /**
     * 得分计算
     * myScore(myX1, myX2, myX3, genghuancishu, xingcheng, yanzhongcishu, zhongjicishu, yibancishu,x1fenmu,x2fenmu,x3fenmu);
     */
    public static String myScore(int myX1, int myX2, int myX3, int genghuancishu, double L, int yanzhongcishu, int zhongjicishu, int yibancishu, int x1fenmu, int x2fenmu, int x3fenmu) {
        //重度事件(当日x1次)的得分：
        double yanzhongscore = softmax(myX1, myX1, myX2, myX3) * fen1(yanzhongcishu, L, x1fenmu);
        //中度事件(当日x2次)的得分：
        double zhongjiscore = softmax(myX2, myX1, myX2, myX3) * fen2(zhongjicishu, L, x2fenmu);
        //轻度事件(当日x3次)的得分：
        double yibanscore = softmax(myX3, myX1, myX2, myX3) * fen3(yibancishu, L, x3fenmu);
        //更换驾驶员事件得分
        double genghuanscore = illegal(genghuancishu);
        //总分数 Score(x)=100- Softmax(6x1)* F1（x1）- Softmax(3x2)* F2（x2）- Softmax(x3) *F3（x3）- I(x).
        double zongscore = 100
                - softmax(myX1, myX1, myX2, myX3) * fen1(yanzhongcishu, L, x1fenmu)
                - softmax(myX2, myX1, myX2, myX3) * fen2(zhongjicishu, L, x2fenmu)
                - softmax(myX3, myX1, myX2, myX3) * fen3(yibancishu, L, x3fenmu)
                - illegal(genghuancishu);
        //封装数据返回
        Map map = new HashMap();
        map.put("genghuanscore", genghuanscore);
        map.put("yanzhongscore", yanzhongscore);
        map.put("zhongjiscore", zhongjiscore);
        map.put("yibanscore", yibanscore);
        map.put("zongscore", zongscore);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
    }


    /**Softmax
     * 设当日总行程为L（km），当日严重报警事件
     * X1次，当日中度事件X2次，轻度事件X3次，则当日三种不同类型事件的权重系数按softmax函数设定为:
     * Softmax(6x1)=e^6x1/ (e^6x1 +e^3x2+ e^x3)
     * Softmax(3x2)= e^3x2/ (e^6x1 +e^3x2+ e^x3)
     * Softmax(x3)= e^x3/ (e^6x1 +e^3x2+ e^x3)
     * @param x  x1xishu * yanzhongcishu
     * @param x1 x1xishu * yanzhongcishu
     * @param x2 x2xishu * zhongjicishu
     * @param x3 x3xishu * yibancishu
     * @return
     */
    public static double softmax(int x, int x1, int x2, int x3) {
        //Math.E=e;    Math.Pow(a,b)=a^b
        double e = Math.E;
        double result = Math.pow(e, x) / (
                Math.pow(e, x1)
                        + Math.pow(e, x2)
                        + Math.pow(e, x3)
        );
        return result;
    }

    /**fen1
     * 得分（100分值扣分）：F1
     * F1（x1）=200（S1（x1）-0.5）；
     * @param x1  yanzhongcishu
     * @return
     */
    public static double fen1(double x1, double L, int x1fenmu) {
        //F1（x1）=200（S1（x1）-0.5）；
        double mysigMoid1 = mysigMoid1(x1, L, x1fenmu);
        double result = 200 * (mysigMoid1 - 0.5);
        return result;
    }

    /**
     * 重度事件(当日x1次)的得分：
     * 对应的sigmoid函数为：S1（x1）=1/（1+e^[-(100x1/L+1)/10];
     * 得分（100分值扣分）：F1（x1）=200（S1（x1）-0.5）；
     * 其中L是当日总里程，100x1/L表示当日的100km的重度事件次数；10的选取，是默认当日100km重度事件达10次，大约F1（x1）=40分；F1（x1）=200（S1（x1）-0.5）的设计是将函数值投射到100分值上。
     * @param x1 yanzhongcishu
     * @param L
     * @return
     */
    public static double mysigMoid1(double x1, double L, int x1fenmu) {
        //S1（x1）=1/（1+e^[-(100x1/L+1)/10];
        double y = -((100 * x1) / L + 1) / x1fenmu;
        double ey = Math.pow(Math.E, y);
        double result = 1 / (1 + ey);
        return result;
    }

    /**fen2
     * 得分（100分值扣分）：F2
     * F2（x2）=200（S2（x2）-0.5）；
     * @param x2  zhongjicishu
     * @return
     */
    public static double fen2(double x2, double L, int x2fenmu) {
        double result = 200 * (mysigMoid2(x2, L, x2fenmu) - 0.5);
        return result;
    }

    /**
     * 中度事件(当日x2次)的得分：
     * 对应的sigmoid函数为：S2（x2）=1/（1+e^[-(100x2/L+1)/20];
     * 得分（100分值扣分）：F2（x2）=200（S2（x2）-0.5）；
     * @param x2  zhongjicishu
     * @return
     */
    public static double mysigMoid2(double x2, double L, int x2fenmu) {
        //S2（x2）=1/（1+e^[-(100x2/L+1)/20];
        double y = -((100 * x2) / L + 1) / x2fenmu;
        double ey = Math.pow(Math.E, y);
        double result = 1 / (1 + ey);
        return result;
    }

    /**
     * 得分（100分值扣分）：F3
     * F3（x3）=200（S3（x3）-0.5）；
     * @param x3 yibancishu
     * @return
     */
    public static double fen3(double x3, double L, int x3fenmu) {
        double result = 200 * (mysigMoid3(x3, L, x3fenmu) - 0.5);
        return result;
    }


    /**
     * 轻度事件(当日x3次)的得分：
     * 对应的sigmoid函数为：S3（x3）=1/（1+e^[-(100x3/L+1)/40];
     * 得分（100分值扣分）：F3（x3）=200（S3（x3）-0.5）；
     * @param x3  yibancishu
     * @return
     */
    public static double mysigMoid3(double x3, double L, int x3fenmu) {
        double y = -((100 * x3) / L + 1) / x3fenmu;
        double ey = Math.pow(Math.E, y);
        double result = 1 / (1 + ey);
        return result;
    }

    /**
     * 违法得分
     * @param time
     * @return
     */
    public static double illegal(int time) {
        return 10 * time;
    }

}
