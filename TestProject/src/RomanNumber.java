public enum RomanNumber {
    XC(90),C(100), XL(40), L(50), IX(9),X(10), IV(4), V(5),I(1);
    private Integer arabicNumber;
    RomanNumber(Integer arabicNumber){
        this.arabicNumber = arabicNumber;
    }
    public Integer getArabicNumber(){
        return arabicNumber;
    }
}
