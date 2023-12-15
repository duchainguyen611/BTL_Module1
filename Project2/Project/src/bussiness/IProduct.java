package bussiness;

import java.io.Serializable;

public interface IProduct extends Serializable {
    public static float MIN_INTEREST_RATE = 0.2F;
    void inputData();
    void displayData();
    void calProfit();
}
