package shared;

import java.util.ArrayList;

public class TeacherDTO extends UserDTO {

	private static final long serialVersionUID = 1L;

	private String study;
	private ArrayList<CourseDTO> courses;
	
	public TeacherDTO() {
		super();
	}

	public TeacherDTO(int id, String mail, String password, String type, String study, ArrayList<CourseDTO> courses) {
		super(id, mail, password, type);
		this.study = study;
		this.courses = courses;
	}

	public String getStudy() {
		return study;
	}

	public void setStudy(String study) {
		this.study = study;
	}
	
	public ArrayList<CourseDTO> getCourses() {
		return courses;
	}
	
	public void setCourses(ArrayList<CourseDTO> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "TeacherDTO [getId()=" + getId() + ", getCbsMail()=" + getCbsMail() + ", getPassword()=" + getPassword()
				+ ", getType()=" + getType() + "]" + ", getStudy()=" + getStudy() + "]" + ", getCourses()=" + getCourses() + "]";
	}
}
