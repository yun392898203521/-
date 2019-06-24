package com.yunshao.score;

/**
 * @Author: yunshao
 * @Date: 2018/8/15 17:19
 */

public class SoftMax {

    /**
     * softMax分类
     * @return label[n][1]
     * @author 后一个X前一个：θ[n][m] * x[m][1]
     * n个标签
     */
    public static double[] mysoftMax(double[][] theta,double[] soft_in) {
        if (theta[0].length != soft_in.length) {
            System.out.println("输入矩阵不匹配，无法进行矩阵运算");
            return null;
        }
        double[] result = new double[theta.length];//返回标签
        double[] value = new double[theta.length];// 分子
        double  denominator = 0.0;//分母
        for (int i = 0; i < result.length; i++) {
            double temValue = 0.0;
            for (int j = 0; j < soft_in.length; j++) {
                temValue += theta[i][j]*soft_in[j];
            }
            value[i] = Math.pow(Math.E, temValue);
            denominator +=Math.pow(Math.E, temValue);
        }
        for (int i = 0; i < result.length; i++) {
            result[i] = value[i]/denominator;
        }
        return result;
    }

    public static void main(String[] args) {



    }

}
