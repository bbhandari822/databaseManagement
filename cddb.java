import java.io.*;
import java.util.*;


public class cddb {

	private static String file_read = "";
	private static String Artist ="";
	private static String Album="";
	private static String track_Info="";
	//private static StringBuffer Track;
	
	// Created a dictionary of artist and album
	private static Map<String, ArrayList<String>> artist_dict = new HashMap<String, ArrayList<String>>();
	private static Map<String, String> album_dict = new HashMap<String, String>();
	

	public static void main(String[] args) throws IOException{
		
		file_read  = readFile();
		
		String value = "-l";
		
	//Takes optional argument.
		
//		String value = (String) args[0];
//		String[] parts = value.split("-");
//		System.out.println(parts[1]);

		//options to choose for users
		if(value.equals("-l")){
			list_album();
		}else if (value.equals("-d")){
			delete_album();
		}else if(value.equals("-a")){
			add_album();
		}else if(value.equals("-h")){
			usage_msg_Nquit();
		}else{
			System.out.println("Sorry there is no command " + value);
		}
	}

	private static void usage_msg_Nquit() {
		
		// Provide detail of how to use the program for users.
		// Print out all information line by line
		
		System.out.println("This is the usage message and help command for users." + "\n");
		System.out.println("User have to provide an argument to maintain a flat-file database of album information");
		System.out.println("Command that can be used are: " + "\n");
		System.out.println("-l :  list all albums by release date, allow user to choose artist, list all tracks.");
		System.out.println("-a : add artist name, album name and all the track of the particular album to the database,");
		System.out.println("-d: delete selected album, artist, and track from the database.");
		System.out.println("-h : show usuage message and quit.");
	}
	
	private static Map<String, String> add_album() {

		//Prompt a user for input
		System.out.println("Please add the album according to the direction");
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter name of Artist: ");
		//takes the first input as Artist
		Artist = keyboard.nextLine();
		System.out.println("Enter the date of album released first and then name of Album: ");
		//takes the second input as Album
		Album = keyboard.nextLine();
		System.out.println("Enter the track of " + Album + " now: ");

		String rawData;
		StringBuffer Track = new StringBuffer();
		//Check until the input doesnot equal to empty and then add to track.
		while (!(rawData = keyboard.nextLine()).isEmpty()) {
			Track.append("-" + rawData);
			track_Info = Track.append("\n").toString();			
			
        } 
		String add_album = addAlbum();
		return album_dict;
	}


	private static String addAlbum() {
		
		//Write the artist, album, track name in database
		try(FileWriter fw = new FileWriter("db.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw)){
			    
				out.println(Artist);
				out.println(Album);
			    out.println(track_Info);
			    
			} catch (IOException e) {
				System.out.println("Error "+ e.getMessage());		
			}
		return null;
	}
	
	private static void delete_album() throws IOException {
		
		//Setting artist dictionary key pair value to array.
		Object[] artist_dict_toArray = artist_dict.keySet().toArray();
		String name_searching_artist = null;
		ArrayList<String> list = null;
		String artist_list = "";
		String album_search = "";				
					
		for(int i=0; i<artist_dict_toArray.length; i++){
			artist_list  += artist_dict_toArray[i] + " ";
		}			
					
		//splitting list from every 2 space for name
		List<String> myList = new ArrayList<String>(Arrays.asList(artist_list.split("(?<!\\G\\S+)\\s")));
		Collections.sort(myList);
		System.out.println(myList);
		
		//Prompt the insrtuction for user.
		System.out.print("\n");
		System.out.println("Press number to choose artist or enter q for quit");
		System.out.println("-------------First index start from 1------------");
		
		//checking the value from user.
		Scanner check_option = new Scanner(System.in);
		String opinion = check_option.nextLine();			
		
		if (opinion.equals("q")){
			System.exit(0);
		}else{
			int num_artist = Integer.parseInt(opinion);
			//creating an array to get the value from user input
			String [] myArray_SortedOrder = myList.toArray(new String[myList.size()]);
			
			for(int i=0; i< artist_dict_toArray.length; i++){
				name_searching_artist = (String) myArray_SortedOrder[num_artist-1]; // getting required name that need to be search.
				
			}
			
			//list all the album of the artist entered.
		for(int i=0; i<artist_dict_toArray.length; i++){
			//check the artist name from array to match user input
			if(name_searching_artist.equals(artist_dict_toArray[i])){
				list = artist_dict.get(name_searching_artist);
				//sort out the list in order
				Collections.sort(list);
				System.out.println(list);
			}
		}				
						
		System.out.println("Please enter # to choose an album or choose a to go back: ");
		Scanner option_user = new Scanner(System.in);
		String option_promp = option_user.nextLine();
		
		//ask user for input, if # enetered then ask user to choose an album
		if(option_promp.equals("#")){
			System.out.println("-------------First index start from 1------------");
			System.out.println("Which album do you choose to delete:");
			String user_enetered_album = option_user.nextLine();
			int num_album_choose = Integer.parseInt(user_enetered_album);
			String [] myArray_SortedOrder_Album = list.toArray(new String[list.size()]);
			for(int i=0; i< album_dict.size(); i++){
				album_search = (String) myArray_SortedOrder_Album[num_album_choose-1]; // getting required name that need to be search.	
			}
			
//			String myEnv_read_delete = System.getenv("CDDB");
//			File original_file = new File(myEnv_read_delete);
			File original_file = new File("db.txt");
			File newFile = new File("cd.db");
			BufferedReader reader = new BufferedReader(new FileReader(original_file));
			BufferedWriter writer = new BufferedWriter(new FileWriter("cd.db",true));
			PrintWriter write = new PrintWriter(writer);
			
			String delete_line = album_search;
			String line_cur;
	
			boolean found = false;
			while((line_cur = reader.readLine())!= null){
				String trim_lin = line_cur.trim();
				if(trim_lin.equals(delete_line)){
					found = true;	
				}
				if ((found == true) && (trim_lin=="")){
					found = false;
				}
				if (found == false){
					 write.println(line_cur);
				}		
			}
			writer.close(); 
			reader.close(); 
			
			
		}else if(option_promp.equals("a")){
			delete_album();
		}else{
			System.out.println("Sorry, there is no option called: " + option_promp);
			
			System.exit(0); //exit if invalid option is provided.
		}
		
		}		
		
	}

	private static void list_album() {

		//Setting artist dictionary key pair value to array.
		Object[] artist_dict_toArray = artist_dict.keySet().toArray();
		String name_searching_artist = null;
		ArrayList<String> list = null;
		String artist_list = "";
		String album_search = "";
		
		for(int i=0; i<artist_dict_toArray.length; i++){
			artist_list  += artist_dict_toArray[i] + " ";
		}
		
		//splitting list from every 2 space for name
		List<String> myList = new ArrayList<String>(Arrays.asList(artist_list.split("(?<!\\G\\S+)\\s")));
		Collections.sort(myList);
		System.out.println(myList);
		
		//Prompt the insrtuction for user, if # enetered then ask user to choose an album
		System.out.print("\n");
		System.out.println("Press number to choose artist or enter q for quit");
		System.out.println("-------------First index start from 1------------");
		
		//checking the value from user.
		Scanner check_option = new Scanner(System.in);
		String opinion = check_option.nextLine();
		if (opinion.equals("q")){
			System.exit(0);
		}else{
			int num_artist = Integer.parseInt(opinion);
			String [] myArray_SortedOrder = myList.toArray(new String[myList.size()]);
			for(int i=0; i< artist_dict_toArray.length; i++){
				name_searching_artist = (String) myArray_SortedOrder[num_artist-1]; // getting required name that need to be search.
				
			}
			
			//list all the album of the artist entered.
			for(int i=0; i<artist_dict_toArray.length; i++){
				if(name_searching_artist.equals(artist_dict_toArray[i])){
					list = artist_dict.get(name_searching_artist);
					Collections.sort(list);
					System.out.println(list);
				}
			}
				System.out.println("Please enter # to choose an album or choose a to go back: ");
				Scanner option_user = new Scanner(System.in);
				String option_promp = option_user.nextLine();
				
				//ask user for input, if # enetered then ask user to choose an album
				if(option_promp.equals("#")){
					System.out.println("Which album do you choose:");
					String user_enetered_album = option_user.nextLine();
					int num_album_choose = Integer.parseInt(user_enetered_album);
					String [] myArray_SortedOrder_Album = list.toArray(new String[list.size()]);
					for(int i=0; i< album_dict.size(); i++){
						album_search = (String) myArray_SortedOrder_Album[num_album_choose-1]; // getting required name that need to be search.	
					}
					System.out.println(album_dict.get(album_search));
				}else if(option_promp.equals("a")){
					list_album();
				}else{
					System.exit(0);
				}
		}
	}

	private static String readFile() {

		ArrayList<String> album_list = null;
		ArrayList<String> track_list = null;
		ArrayList<String> track_list_new = null;
		
		try {
			
//			String myEnv_read = System.getenv("CDDB");
//			FileReader fileReader = new FileReader(myEnv_read);
//			BufferedReader freader = new BufferedReader(fileReader);
			
			FileReader fileReader = new FileReader("db.txt");
            BufferedReader freader = new BufferedReader(fileReader);
            StringBuffer read_file_database = new StringBuffer();
            String rawData;
            
            while ((rawData = freader.readLine()) != null) {
            	//reading every first line as artist name
            	Artist = rawData;
            	//reading every second line as album
            	Album = freader.readLine();
            	//checking if there is multiple album of same artist name
            	//adding in the album list if artist found
            	if(!(artist_dict.containsKey(Artist))){
            		album_list = new ArrayList<String>();
            		album_list.add(Album);
            	}else{
            		album_list.add(Album);
            	}
            	
            	//creating a artist dictionary with artist name  as key and album list as value.
            	artist_dict.put(Artist, album_list);
            	
            	//creating a tracklist
            	track_list = new ArrayList<String>(Arrays.asList(track_Info));
            	while (!(rawData = freader.readLine()).equals("")) {
            		//track_Info =  rawData;
            		track_list.add(rawData);
            	}
            	//creating arraylist and deleting the first null character
            	String[] myArray_track = track_list.toArray(new String[track_list.size()]);
            	String my_track_list = "";
            	
            	for(int i = 1; i<myArray_track.length; i++){
            		my_track_list += myArray_track[i] + "\n";
            	}
            	track_list_new = new ArrayList<String>(Arrays.asList(my_track_list));
            	//creating a dictionary with album as key and track_list as value.
            	album_dict.put(Album, track_list_new.toString());	
            	
            }  
            
            fileReader.close();
            file_read = read_file_database.toString();
		} catch (IOException e) {
			System.out.println("Error "+ e.getMessage());
		}
		//System.out.println(file_read);
		return file_read;
	}
}
