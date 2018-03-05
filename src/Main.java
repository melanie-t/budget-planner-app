
public class Main {

	public static void main(String[] args) throws ClassNotFoundException
    {
		
		runMainApp();
		//runScratchApp();
    }
	
	
	public static void runMainApp() {
		Iteration1AppController App = new Iteration1AppController();
		App.start();
		App.run();
		App.shutdown();
	}
	
	
	public static void runScratchApp() {
		//Can run code here when doing R&D
		
		
	}
}
