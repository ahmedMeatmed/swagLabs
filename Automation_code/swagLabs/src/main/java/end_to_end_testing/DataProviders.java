package end_to_end_testing;
import org.testng.annotations.DataProvider;


public class DataProviders {


    @DataProvider(name = "users")
    public Object[][] users(){
        return new Object[][] {
                {"standard_user", "secret_sauce"},
                {"problem_user", "secret_sauce"},
                {"problem_user", "invalid_Password"},//invalid Password
                {"invalid_user", "secret_sauce"},//invalid user
        };
    }
}
