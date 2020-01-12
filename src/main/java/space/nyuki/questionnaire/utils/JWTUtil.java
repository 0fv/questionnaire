package space.nyuki.questionnaire.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

public class JWTUtil {
	private static final int EXPIRE_TIME = 500 * 1000 * 60 * 60;
//    private static final int EXPIRE_TIME = 1;

	/**
	 * 验证token正确性
	 *
	 * @param token
	 * @param username
	 * @param secret
	 * @return
	 */
	public static boolean verify(String token, String username, String secret) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm)
					.withClaim("username", username)
					.build();
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (IllegalArgumentException | JWTVerificationException e) {
			return false;
		}
	}

	/**
	 * 获取token中的用户名
	 *
	 * @param token
	 * @return
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 生成签名
	 *
	 * @param username
	 * @param secret
	 * @return
	 */
	public static String sign(String username, String secret) {
		try {
			Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
			secret = MD5Util.saltMd5(secret);
			Algorithm algorithm = Algorithm.HMAC256(secret);
			// 附带username信息
			return JWT.create()
					.withClaim("username", username)
					.withExpiresAt(date)
					.sign(algorithm);
		} catch (IllegalArgumentException | JWTVerificationException e) {
			return null;
		}
	}
}
