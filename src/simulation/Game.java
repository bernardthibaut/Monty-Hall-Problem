package simulation;
import java.util.Random;

public class Game {

	Random r = new Random();

	int iterations = 0;

	String[] choises = new String[3];

	String g = "Goat";
	String c = "Car";

	int nbGoats = 0;
	int nbCars = 0;

	int ggc = 0;
	int gcg = 0;
	int cgg = 0;

	int pick;

	int tryWon = 0;
	int tryLost = 0;

	public Game() {

		int numberIterations = 500000;

		System.out.println("Number of iterations : " + numberIterations + '\n');

		System.out.println("<-" + '\t' + "Simulation without changing your pick" + '\t' + "->" + '\n');
		for (int i = 0; i < numberIterations; i++) {
			simulateWithoutChanging();
		}
		showResult();

		tryWon = 0;
		tryLost = 0;
		iterations = 0;
		
		System.out.println('\n' + "<-" + '\t' + "Simulation changing your pick" + '\t' + "->" + '\n');
		for (int i = 0; i < numberIterations; i++) {
			simulateChanging();
		}
		showResult();

	}

	public void draft() {
		choises = new String[3];
		for (int j = 0; j < 3; j++) {
			int n;

			if (j == 0) {
				n = r.nextInt(3);
				if (n == 0 || n == 1) {
					choises[j] = g;
					nbGoats++;
				} else {
					choises[j] = c;
					nbCars++;
				}
			} else if (j == 1) {
				n = r.nextInt(2);
				if (n == 0) {
					choises[j] = g;
					nbGoats++;
				} else if (nbCars == 0) {
					choises[j] = c;
					nbCars++;
				} else {
					choises[j] = g;
					nbGoats++;
				}
			} else if (j == 2) {
				if (nbGoats == 2) {
					choises[j] = c;
				} else {
					choises[j] = g;
				}
				nbGoats = 0;
				nbCars = 0;
			}
		}

		if (choises[0] == g && choises[1] == g && choises[2] == c) {
			ggc++;
		} else if (choises[0] == g && choises[1] == c && choises[2] == g) {
			gcg++;
		} else if (choises[0] == c && choises[1] == g && choises[2] == g) {
			cgg++;
		}
		iterations++;
	}

	public void openDoor() {

		int pickTmp = pick;
		String sTmp = null;
		String[] choisesTmp = new String[2];
		do {
			pick = pickTmp;
			sTmp = null;
			choisesTmp = new String[2];
			int n = r.nextInt(3);
			for (int i = 0; i < 3; i++) {
				if (i != n) {
					if (choisesTmp[0] == null)
						choisesTmp[0] = choises[i];
					else
						choisesTmp[1] = choises[i];
				} else {
					if (pick != i) {
						sTmp = choises[i];
						if (i == 0) {
							pick--;
						} else if (i == 1) {
							if (pick == 2)
								pick--;
						}
					}
				}
			}
		} while (sTmp != g);
		choises = choisesTmp;

	}

	public void showMix() {
		int rateCCV = (int) ((double) ggc / (double) iterations * 100);
		int rateCVC = (int) ((double) gcg / (double) iterations * 100);
		int rateVCC = (int) ((double) cgg / (double) iterations * 100);

		System.out.println("CCV : " + ggc + '\t' + rateCCV + " % ");
		System.out.println("CVC : " + gcg + '\t' + rateCVC + " % ");
		System.out.println("VCC : " + cgg + '\t' + rateVCC + " % ");
		System.out.println("Total : " + (ggc + gcg + cgg));
	}

	public void showDoors() {
		for (int i = 0; i < choises.length; i++) {
			if (i == pick)
				System.out.println("Porte " + (i + 1) + " : " + '\t' + choises[i] + '\t' + "<- Pick");
			else 
				System.out.println("Porte " + (i + 1) + " : " + '\t' + choises[i]);
		}
		System.out.print('\n');
	}

	public void pickDoor() {
		pick = r.nextInt(choises.length);
	}

	public void simulateWithoutChanging() {
		draft();
		pickDoor();
		openDoor();
		if (choises[pick] == c)
			tryWon++;
		else
			tryLost++;
	}

	public void simulateChanging() {
		draft();
		pickDoor();
		openDoor();
		if (pick == 0)
			pick = 1;
		else
			pick = 0;

		if (choises[pick] == c)
			tryWon++;
		else
			tryLost++;
	}

	public void showResult() {
		int rateWon = (int) ((double) tryWon / (double) iterations * 100);
		int rateLost = (int) ((double) tryLost / (double) iterations * 100);

		System.out.println("Won : " + tryWon + '\t' + rateWon + " %");
		System.out.println("Lost : " + tryLost + '\t' + rateLost + " %");
	}

	public static void main(String[] args) {
		new Game();
	}

}
