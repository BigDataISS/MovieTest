package Bean;

/**
 * Start
 * 实体类，用于初始化echart信息
 * @author 张子健，王鑫科
 **/

public class EchartMovie {
	private String name;
	private Integer value;
	
	public EchartMovie(String name,Integer value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
}

/**
 * End
 * 实体类，用于初始化echart信息
 * @author 张子健，王鑫科
 **/
