
public class Main {

	public static void main(String[] args) throws ClassNotFoundException
    {
		//*
		runIteration2App();
		/*/
		runDummyApp();
		//*/
    }
	
	
	public static void runIteration2App() {
		//Can run code here when doing R&D
		Iteration2AppController App = new Iteration2AppController();
		App.start();
		App.run();
		App.shutdown();
	}
	
	
	public static void runDummyApp() {
		//Can run code here when doing R&D
		DummyAppController App = new DummyAppController();
		App.start();
		App.run();
		App.shutdown();
	}
}
