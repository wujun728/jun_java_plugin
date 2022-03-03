package org.springrain.frame.cache;

import java.util.List;
import java.util.Set;

/**
 * 文档参考 http://redisdoc.com <br/>
 * https://redis.io/commands
 * 
 * @author caomei
 *
 */
public interface ICache {
	/**
	 * 删除给定的一个或多个 key,不存在的 key 会被忽略。
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	String del(byte[] key) throws Exception;

	/**
	 * 将字符串值 value 关联到 key,如果 key 已经持有其他值， SET 就覆写旧值，无视类型. 对于某个原本带有生存时间（TTL）的键来说， 当
	 * SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */

	void set(byte[] key, byte[] value) throws Exception;

	/**
	 * 将字符串值 value 关联到 key,如果 key 已经持有其他值， SET 就覆写旧值，无视类型. 对于某个原本带有生存时间（TTL）的键来说， 当
	 * SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。
	 * 
	 * @param key
	 * @param value
	 * @param expireMillisecond
	 * @return
	 * @throws Exception
	 */
	void set(byte[] key, byte[] value, Long expireMillisecond) throws Exception;

	/**
	 * 将 key 的值设为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。 SETNX 是『SET if
	 * Not eXists』(如果不存在，则 SET)的简写。
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	Boolean setNX(byte[] key, byte[] value) throws Exception;

	/**
	 * 将 key 的值设为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。 SETNX 是『SET if
	 * Not eXists』(如果不存在，则 SET)的简写。
	 * 
	 * @param key
	 * @param value
	 * @param expireMillisecond
	 * @return
	 * @throws Exception
	 */
	Boolean setNX(byte[] key, byte[] value, Long expireMillisecond) throws Exception;

	/**
	 * 返回 key 所关联的字符串值。 如果 key 不存在那么返回特殊值 nil 。 假如 key 储存的值不是字符串类型，返回一个错误，因为 GET
	 * 只能用于处理字符串值。
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Object get(byte[] key) throws Exception;

	/**
	 * 查找所有符合给定模式 pattern 的 key 。 KEYS * 匹配数据库中所有 key 。 KEYS h?llo 匹配 hello ， hallo
	 * 和 hxllo 等。 KEYS h*llo 匹配 hllo 和 heeeeello 等。 KEYS h[ae]llo 匹配 hello 和 hallo
	 * ，但不匹配 hillo 。
	 * 
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	Set keys(byte[] keys) throws Exception;

	Set hKeys(byte[] key) throws Exception;

	Boolean hSet(byte[] key, byte[] mapkey, byte[] value, Long expireMillisecond) throws Exception;

	Object hGet(byte[] key, byte[] mapkey) throws Exception;

	Long hDel(byte[] key, byte[] mapkey) throws Exception;

	Long hLen(byte[] key) throws Exception;

	List hVals(byte[] key) throws Exception;

	Long dbSize() throws Exception;

	void flushDb() throws Exception;
}
