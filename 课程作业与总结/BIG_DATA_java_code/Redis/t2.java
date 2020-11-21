import java.util.Map;

import redis.clients.jedis.Jedis;


public class t2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost");
		
		String value = jedis.hget("student.scofield", "English");
		System.out.println("student.scofield English: " + value);
	}

}
