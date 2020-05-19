


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


public class TextAnalyzer
{
  public static void main(String[] args) throws IOException, InterruptedException {
//    try {
//      UIManager.setLookAndFeel(new DarculaLaf());
//    } catch (Exception e) {
//      
//      e.printStackTrace();
//    } 
    
    Scanner scan = new Scanner(System.in);
    boolean stop = true;
    String ans = "start";

    
    while (stop) {
      
      if (ans.equalsIgnoreCase("y")) {
        Run();
        ans = ""; continue;
      } 
      if (ans.equalsIgnoreCase("n")) {
        for (int i = 3; i >= 1; i--) {
          Thread.sleep(1000L);
          System.out.println("Program closing in " + i + " sec:");
        } 
        stop = false; continue;
      } 
      if (ans.equalsIgnoreCase("")) {
        System.out.println("Start Again? Y / N");
        ans = scan.nextLine(); continue;
      } 
      if (ans.equalsIgnoreCase("start")) {
        
        for (int i = 1; i <= 3; i++) {
          Thread.sleep(1000L);
          System.out.println("Program starting in " + i + " sec:");
        } 
        
        ans = "y";
        continue;
      } 
      System.out.println("Wrong input! type again");
      ans = scan.nextLine();
    } 

    
    scan.close();
  }
  
  public static void Run() throws IOException {
    String filename = "";

    
    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
    jfc.setDialogTitle("Select a Text file!");
    jfc.setAcceptAllFileFilterUsed(false);
    FileNameExtensionFilter ext = new FileNameExtensionFilter("Only .txt file", new String[] { "txt" });
    jfc.addChoosableFileFilter(ext);
    
    int returnValue = jfc.showOpenDialog(null);

    
    if (returnValue == 0) {
      File selectedFile = jfc.getSelectedFile();
      System.out.println(selectedFile.getAbsolutePath());
      filename = selectedFile.getAbsolutePath();
    } 
    
    System.out.println("=====================================================");
    LetterCount(filename);
    System.out.println("=====================================================");
    FrequencyLetter(filename);
    System.out.println("=====================================================");
    WordCounts(filename);
    System.out.println("=====================================================");
    LongAndShortWordLen(filename);
    System.out.println("=====================================================");
    Unigram(filename);
    System.out.println("=====================================================");
    Bigram(filename);
    System.out.println("=====================================================");
  }
  
  public static void LetterCount(String filePath) throws IOException {
    FileInputStream fileStream = new FileInputStream(filePath);
    InputStreamReader input = new InputStreamReader(fileStream);
    BufferedReader reader = new BufferedReader(input);
    
    int charCount = 0;
    String data;
    while ((data = reader.readLine()) != null) {
      charCount += data.length();
    }
    System.out.println("Number of characters: " + charCount);
  }
  
  public static void FrequencyLetter(String filepath) throws FileNotFoundException {
		 Map<Character, Integer> hashMap = new HashMap<Character, Integer>();
	    
	        Scanner scanner = new Scanner(new File(filepath));
	        while (scanner.hasNext()) {
	            char[] chars = scanner.nextLine().toLowerCase().toCharArray();
	            for (Character c : chars) {
	                if(!Character.isLetter(c)){
	                    continue;
	                }
	                else if (hashMap.containsKey(c)) {
	                    hashMap.put(c, hashMap.get(c) + 1);
	                } else {
	                    hashMap.put(c, 1);
	                }
	            }
	        }
	        
	        
	        System.out.println("Frequency of letters in the text in descending order: ");
	        
	        
	        AtomicInteger idx = new AtomicInteger();
			idx.set(1);
			//Print out the frequency of the words in descending order.
			hashMap.entrySet().stream()
			.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
			.limit(20)
	        .forEach(item -> {
	        	System.out.println(idx.getAndIncrement() + ") " + item);
	        });
			
	}


  
  public static void WordCounts(String filename) throws IOException {
    Scanner txtFile = new Scanner(new File(filename));
    
    int countwords = 0;
    
    while (txtFile.hasNext()) {
      String word = txtFile.next();

      
      if (txtFile.hasNext())
      {
        countwords++;
      }
    } 
    
    txtFile.close();
    
    System.out.println("Number of words: " + countwords);
  }
  
  public static void Unigram(String filename) throws FileNotFoundException{
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		Scanner txtFile = new Scanner(new File(filename));

		while(txtFile.hasNext()) {
			String word = txtFile.next();

			//Count the frequency of the word
			if(map.containsKey(word)) {
				int count = map.get(word) + 1;
				map.put(word, count);
			}
			else {
				map.put(word, 1);
			}
			
		}
		//Close the text file
		txtFile.close();
		
		System.out.println("The twenty most repeated uni-grams (single words) in the text in descending order:");
	
		AtomicInteger idx = new AtomicInteger();
		idx.set(1);
		//Print out the frequency of the words in descending order.
		map.entrySet().stream()
		.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
		.limit(20)
      .forEach(item -> {
      	System.out.println(idx.getAndIncrement() + ") " + item);
      });
	}

  
  public static void Bigram(String filename) throws FileNotFoundException{
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		Scanner txtFile = new Scanner(new File(filename));
		
		
		while(txtFile.hasNext()) {
			
			String word = txtFile.next();
			String temp = word;
			if (txtFile.hasNext()) {
				word += " " + txtFile.next();
              //word = temp + " " + word;
			}
			
			//Count the frequency of the word
			map.merge(word, 1, Integer::sum);
			
		}
		//Close the text file
		txtFile.close();
		
		System.out.println("The twenty most repeated bi-grams (pairs of words) in the text in descending order:");
		
		AtomicInteger idx = new AtomicInteger();
		idx.set(1);
		//Print out the frequency of the words in descending order.
		map.entrySet().stream()
		.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
		.limit(20)
      .forEach(item -> {
      	System.out.println(idx.getAndIncrement() + ") " + item);
      });
	}


  
  public static void LongAndShortWordLen(String filename) throws IOException {
    Scanner txtFile = new Scanner(new File(filename));
    int maxLen = 0;
    int mimLen = Integer.MAX_VALUE;
    String longString = "";
    String shortString = "";
    
    while (txtFile.hasNext()) {
      
      String word = txtFile.next();
      int wlen1 = word.length();

      
      if (wlen1 > maxLen) {
        
        maxLen = wlen1;
        longString = word;
      } 
      
      if (wlen1 < mimLen) {
        
        mimLen = wlen1;
        shortString = word;
      } 
    } 

    
    txtFile.close();
    
    System.out.println("Size of the longest word: " + maxLen + " | word: " + longString);
    System.out.println("Size of the shortest word: " + mimLen + " | word: " + shortString);
  }
}
