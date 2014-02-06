import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Simulator {
	
	byte[] byteArray;
	byte[] sampleArray;
	
	public static void main(String[] args) {
		Simulator run = new Simulator();
		try {
			run.readFile();
			run.networkSimulation(run.byteArray);
			run.writeFile(run.sampleArray);

			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("sumfing went wrong");
		}
		
	}
	
	public void readFile() throws IOException{
	    File soundFile = new File("/Users/Kine/Documents/GitHub/Network_Computing/HW2_Audio/src/canttakeit.au");
	    byteArray = new byte[(int) soundFile.length()];
	    DataInputStream dis = new DataInputStream(new FileInputStream(soundFile));
	    dis.readFully(byteArray);
	    dis.close();
	}
	
	public void networkSimulation(byte[] bArray){
		sampleArray = new byte[byteArray.length];
		for (int i = 0; i < bArray.length; i++) {
			int loss = calculateLossProb();
			int threshold = 80;
			System.out.println("Byte: " + bArray[i]);
			if (loss < threshold){
				//storeSample(bArray[i]);
				sampleArray[i] = bArray[i];
			}
			else{
				bArray[i] = 0;
				sampleArray[i] = bArray[i];

			}
		}
	}
	
//	private void storeSample(byte b) {	
//	}

	public int calculateLossProb(){
		int prob = 0;
		prob = (int)(Math.random()*100);
		//System.out.println("Prob: " + prob);
		return prob;
	}
	
	public void writeFile(byte[] byteArray) throws IOException{
		//write the output sound file
		FileOutputStream fos = new FileOutputStream("/Users/Kine/Documents/GitHub/Network_Computing/HW2_Audio/src/output.au");
		fos.write(byteArray);
		fos.close();
	}
	
	
	
	
}