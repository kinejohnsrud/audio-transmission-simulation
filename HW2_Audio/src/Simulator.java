import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
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
		} 
		catch (IOException e) {
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
		byte lastSampledByte = 103;
		//one "packet" = one byte
		for (int i = 0; i < bArray.length; i++) {
			int loss = calculateLossProb();
			int threshold = 80;
			System.out.println("Byte: " + bArray[i]);
			if (loss < threshold){
				sampleArray[i] = bArray[i];
				lastSampledByte = bArray[i];
			}
			else{
				//bArray[i] = 0;
				//sampleArray[i] = bArray[i];
				sampleArray[i] = lastSampledByte;
			}
		}
	}

	public int calculateLossProb(){
		int prob = 0;
		prob = (int)(Math.random()*100);
		return prob;
	}
	
	public void writeFile(byte[] byteArray) throws IOException{
		FileOutputStream fos = new FileOutputStream("/Users/Kine/Documents/GitHub/Network_Computing/HW2_Audio/src/output.au");
		fos.write(byteArray);
		fos.close();
	}
	
}