import java.io.File;
import java.nio.file.spi.FileTypeDetector;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


public class Simulator {
	
	public static void main(String[] args) {

		//READING A SOUND FILE IN JAVA
		int totalFramesRead = 0;
		File fileIn = new File("/Users/Kine/Documents/GitHub/Network_Computing/HW2_Audio/src/canttakeit.au");
		
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
			int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
			int numBytes = 1024 * bytesPerFrame;
			byte[] audioBytes = new byte[numBytes];
			
			try {
				int numBytesRead = 0;
				int numFramesRead = 0;
				
				while((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
					numFramesRead = numBytesRead / bytesPerFrame;
					totalFramesRead += numFramesRead;
					//HERE: do something useful with the data in the audioBytes array
					int threshold = 75;
					for (int i = 0; i < audioBytes.length; i++) {
						int loss = calculateLossProbability();
						if(loss < threshold){
							storeSample(audioBytes[i]);
						}
						else{	//what to do when loss happens
							audioBytes[i] = 0;
							storeSample(audioBytes[i]);
						}	
					}
				}
				System.out.println("Bytes per frame: " + bytesPerFrame);
				System.out.println("Number of bytes: " + numBytes);
				System.out.println(audioBytes[0] + " and " + audioBytes[1] + " and " + audioBytes[2] + " and " + audioBytes[3] + " and " + audioBytes[4] + " and " + audioBytes[5]);
			
				//THE BASICS OF WRITING A SOUND FILE IN JAVA
//				File fileOut = new File("/Users/Kine/Documents/GitHub/Network_Computing/HW2_Audio/src/copy.au");
//				AudioFileFormat.Type fileType = AudioFileFormat.Type.AU;
//				if (AudioSystem.isFileTypeSupported(fileType, audioInputStream)) {
//					AudioSystem.write(audioInputStream, fileType, fileOut);
//				}
			
			} catch (Exception e){
				System.out.println("wrong!");
			}
		} catch (Exception e){
			System.out.println("wrong2!");

		}
	}

	private static void storeSample(byte b) {
		// TODO Auto-generated method stub
		
	}

	//loss probability should be low, medium and high
	private static int calculateLossProbability() {
		// TODO Auto-generated method stub
		return 0;
	}
}
