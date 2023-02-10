public class WrongDetails extends Throwable{
    WrongDetails() {
        super("Username or password or both is wrong");
    }
}
