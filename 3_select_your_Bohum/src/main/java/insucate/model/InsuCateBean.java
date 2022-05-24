package insucate.model;

import org.hibernate.validator.constraints.NotBlank;

public class InsuCateBean {
	private int num;
	
	@NotBlank(message="ī�װ��� �����ϼ���.")
	private String category;
	
	public InsuCateBean() {
		super();
	}

	public InsuCateBean(int num, String category) {
		super();
		this.num = num;
		this.category = category;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
