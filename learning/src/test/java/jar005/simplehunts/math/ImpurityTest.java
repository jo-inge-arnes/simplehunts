package jar005.simplehunts.math;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

public class ImpurityTest {
    private final double delta = 0.00000001d;

    @Test
    public void When_AllValuesAreTheSame_Expect_ZeroMisclassificationError() {
        Boolean[] values = {true, true, true, true};

        double impurity = Impurity.misclassificationError(Arrays.asList(values));

        Assert.assertEquals(impurity, 0.0);
    }

    @Test
    public void When_OneFalseFourTrue_Expect_PointTwoMisclassificationError() {
        Boolean[] values = {false, true, true, true, true};

        double impurity = Impurity.misclassificationError(Arrays.asList(values));

        Assert.assertEquals(impurity, 0.2, delta);
    }

    @Test
    public void When_TwoFalseTwoTrue_Expect_PointFiveMisclassificationError() {
        Boolean[] values = {false, true, true, false};

        double impurity = Impurity.misclassificationError(Arrays.asList(values));

        Assert.assertEquals(impurity, 0.5, delta);
    }
}
