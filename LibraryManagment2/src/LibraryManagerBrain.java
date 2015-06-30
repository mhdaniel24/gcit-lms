import java.util.ArrayList;

import org.xml.sax.InputSource;
//CURDATE(), date_add(CURDATE(), INTERVAL 7 DAY)

public class LibraryManagerBrain {

	User user;
	ArrayList<String> userInputSoFar;
	String messageToShow;
	
	public LibraryManagerBrain(){
		userInputSoFar = new ArrayList<String>();
		messageToShow = new String();
	}

	public String getMessageToShowNoSpectedInput() {
		return messageToShow;
	}

	public String getMenu() {
		if(userInputSoFar.size() == 0){
			//return ""
		}
		return null;
	}

	public boolean userInputWas(String readUserInput) {
		return false;
	}
	
	
}
