package shared;

public class AdminDTO extends UserDTO {

	private static final long serialVersionUID = 1L;

	public AdminDTO() {
		super();
	}

	public AdminDTO(int id, String cbsMail, String password, String type) {
		super(id, cbsMail, password, type);
	}

	@Override
	public String toString() {
		return "AdminDTO [getId()=" + getId() + ", getCbsMail()=" + getCbsMail() + ", getPassword()=" + getPassword()
				+ ", getType()=" + getType() + "]";
	}
}
