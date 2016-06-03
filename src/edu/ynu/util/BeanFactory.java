package edu.ynu.util;

/**
 * description: bean工厂类
 * @author cdsnow2017@163.com
 * @since 2015年11月20日 下午9:12:18
 * @version 1.0
 */
public class BeanFactory {

	/**
	 * description: 得到一个状态实例
	 * @param info 状态码	
	 * @return 
	 */
	public static State getState(String info) {
		State state = new State();
		state.setState(info);
		return state;
	}

}
