package com.twinetree.juice.api;

public class Url {

    public static String accessToken = "EkEF-CudU2WRmq9lusXBDE2xtvpjibd_Vm66cNJj2qA-yRse4CV9KWwSRDlugqHaiiVaRx96Ppm5qRArP52DIgxbec2sfL5l7r9g7LygyL_UzyJa1CAYzpzvOgbpH5VsaHVSgIwQRoiS0cQ1V9JYqakkffJVafRIjVenRb3unVP4MyhV9cvTEHo267m9p0hHC39xHbaTQPQotujG5rAAbJMfFxhF_u8ZC971W7E45e-C0Ih1JUekQLPsCm14tzfYsHFZZmf-Mqaue-F3wjFa-hPj-dpWoDaD7vXPfVESit3vd1la8aOgvcIcVPQfaGSSV3a9rdYPiYlIlf38gnG8mW0geD-jiqkXeBU1hmuzM0rPsv0O4IFuv9A9zoqicruxoDbK2qR7h7CLfwsN6gOaMiM0XLcoBH9IEH1rMRzc4CCe2gZOKaSoRAm5Eqho5m7N";

    public static String getQuestions(int pageIndex, int pageSize) {
        return "http://joos.azurewebsites.net/api/question/get?pageIndex=" + Integer.toString(pageIndex)
        + "&pageSize=" + Integer.toString(pageSize);
    }

    public static String addQuestion = "http://joos.azurewebsites.net/api/question/add";
}
