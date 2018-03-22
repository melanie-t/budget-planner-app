public class Main {

	public static void main(String[] args) throws ClassNotFoundException
    {
		runIteration2App(args);
		//runDummyApp();
    }
	
	
	public static void runIteration2App(String[] args) {
		//Can run code here when doing R&D
        User user;
        if (args.length == 2)
        {
            user = new User(args[0], "", Integer.parseInt(args[1]));
        }
        else
        {
            user = new User();
        }
		Iteration2AppController App = new Iteration2AppController(user);
		App.start();
		App.run();
	}
	
	
//	public static void runDummyApp() {
//		//Can run code here when doing R&D
//		DummyAppController App = new DummyAppController();
//		App.start();
//		App.run();
//		App.shutdown();
//	}
}
