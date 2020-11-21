import java.util.Map;

import redis.clients.jedis.Jedis;


public class t1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost");
		jedis.hset("student.scofield", "English", "45");
		jedis.hset("student.scofield", "Math", "89");
		jedis.hset("student.scofield", "Computer", "100");
		
		Map<String, String> value = jedis.hgetAll("student.scofield");
		for (Map.Entry<String, String> entry : value.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}

}
