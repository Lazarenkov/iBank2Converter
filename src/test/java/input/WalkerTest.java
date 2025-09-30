package input;

import config.Props;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

public class WalkerTest {

    static MockedStatic<Props> utilityProps = Mockito.mockStatic(Props.class);


    @Test
    void test() throws IOException {
        utilityProps.when(() -> Props.getInputPath()).thenReturn("C:\\Users\\COMP\\IdeaProjects\\iBank2Converter\\io\\input");
        InputWalker.walk();
        System.out.println(InputWalker.hasNextPath());
        System.out.println(InputWalker.getNextPath());
        System.out.println(InputWalker.getCurrentPath().getFileName());
        System.out.println(InputWalker.getCurrentFileName());
    }

}
