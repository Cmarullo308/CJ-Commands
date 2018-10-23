package Tools;
import java.util.Vector;

public class Contact {
	private String firstName;
	private Vector<String> middleNames;
	private String lastName;
	private int age;
	private String email;

	public Contact() {
		firstName = "NoName";
		middleNames = new Vector<String>();
	}

	public Contact(String firstName) {
		this.firstName = firstName;
		middleNames = new Vector<String>();
	}

	public Contact(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		middleNames = new Vector<String>();
	}

	public String toString() {
		String temp = "";

		if ((firstName == null || firstName.equals("")) && middleNames.size() == 0
				&& (lastName == null || lastName.equals(""))) {
			return "NoName";
		}

		if ((firstName != null && !firstName.equals(""))) {
			temp += firstName + " ";
		}

		if (middleNames.size() > 0) {
			for (int i = 0; i < middleNames.size(); i++) {
				temp += middleNames.get(i) + " ";
			}
		}

		if (lastName != null && !lastName.equals("")) {
			temp += lastName;
		}

		return temp;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Vector<String> getMiddleNames() {
		return middleNames;
	}

	public void setMiddleNames(Vector<String> middleNames) {
		this.middleNames = middleNames;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
