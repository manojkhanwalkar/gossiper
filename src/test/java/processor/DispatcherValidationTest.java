package processor;

import data.User;
import event.AddPost;
import event.AddUser;
import event.GetUser;
import org.junit.Test;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

public class DispatcherValidationTest {

    private final Dispatcher dispatcher = new Dispatcher();

    @Test(expected = BadRequestException.class)
    public void addUserWithoutUserIsRejected() {
        dispatcher.dispatch(new AddUser());
    }

    @Test(expected = BadRequestException.class)
    public void oversizedUserIdIsRejected() {
        User user = new User();
        user.setId("x".repeat(Dispatcher.MAX_ID_LENGTH + 1));
        user.setName("name");
        AddUser addUser = new AddUser();
        addUser.setUser(user);
        dispatcher.dispatch(addUser);
    }

    @Test(expected = BadRequestException.class)
    public void postWithoutBodyIsRejected() {
        dispatcher.dispatch(new AddPost());
    }

    @Test(expected = NotFoundException.class)
    public void unknownUserIsNotFound() {
        GetUser getUser = new GetUser();
        getUser.setUserId("no-such-user");
        dispatcher.dispatch(getUser);
    }
}
