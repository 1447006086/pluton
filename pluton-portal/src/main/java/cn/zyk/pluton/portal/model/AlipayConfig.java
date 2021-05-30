package cn.zyk.pluton.portal.model;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2021000117648832";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCc/yxF3vpyXrd3qS2fQWusyxZu5LyV2QQVWogwAB8Z01P1aXKE1BE9rAWV2yJIu6pwXyEtlT0etrdRHm7UioC8B/UQ5pLl21fRStXvF2znriU4tC8g6kEOLoqvbksyfugck9URdBW3nGLWNvWlMVjYj2pXlPIi4/K+y6JPmbhI9C9IatmiAYj0qkDMp2qNIGeFfw20SQkYfGouS2ywZoEoMUZLGqlgafLS9EF315xNSwb9ul2X75JUWqtnYJ8ZR/AyZm9W8LqUGet1MmAU6wP7/RJfttP2OCMRJKplgspCZuKq7MyHVkn62Vt7eedT8hub26J6+oHQrKUnUwXMjQnpAgMBAAECggEAHCGA7lgg6iNcnn1a5mFTiNevAlnMDNGg8K6THn63E+qQ7bViRri1CibrrUlhoES4KoBxMni+ReWZfViFpWHX5VlLqSaXhcZCuwa8xKWe5viGRN3MYAp5c7AVO4/4u2iP8Kbwk7sINSk5cIbShzILaGpyNJVEucm8ckGMqNSzd6a43Hr8A1n+JjvqQG6pUEQ0P3PJyytBpb7ttuTWRIghF10Qkq6p/Im+5tH0iInu+cI5sNQuKinb6xDXSqFM159+GbMfqA4o/Sf9+NjflvK2qmszgIpzWk8spqqGUIjCgZN/wyRUGUY0rbi9Y7XFnYpZljLwAJERj7MC0WsO7+YiLQKBgQDlmn8pYovZbJ/qeVRyNjsN5up81exZfNlrm7KvrtAirtdRRXyHCIrdLFPJ460On+79fezidnqVFN5t11ccIkGlgKPNkl0G0z8HznT3bGsOBe+1qG2958xxUWks8rmVkvv5dboP5pqJtEJTpM2vE0uefNfJ2+LkyCb/9g498QYMDwKBgQCvC8YjQi9Ckacit3993gvFzLV1AkQT2Gc1S4mmJsqtDZbpc5g7x5p4Pa8FnkV3Kg1qa/0zqX1nAKP5eGSPkTA9zmagkKGLWcMfzTh7vL7ieAcsMb0FpWAvRzHz7mnUNkB6+M9B1fwVpaE7DyzmO7lomF2MuVD9Ijvcr7wFKSJyhwKBgBVAcZixCGS9iye2xzwzDNBCmV2op1G5Hp65MJbWRWmQaVmaZuZQwhmqqq67Jh9+ai6IY06qzLnTzqq4DdYKzay19XNnoripAOKGF4l3PYl0vzU/O7kiJWgvst54MPlJO41hejFSlOc+Cg2X6XnU/WDUCV0jvyKR7Bow+ix9jKO9AoGBAKRzSUhiq4e80uqYvhv/+ctwkKFG6bJDQmMdfkosc4i1FaxpPwIaehKw0LdUhCp5xXTP02cvunKEdYiwBLmkS1AlkuESP9JtGBXOdVw6bJLS9NoeOFkPjxebqWBkAeSuu1FMY1qUsGmd26VikmoSfhO34VuN1nqaKOch6mBmzsgNAoGBANuZNsFm8v26PjtAxmWq2IvqHxFFKeoucoLd9xyt5EovFzCgpblrQ0rMjE1FheKsaZcJVkTL1KmwLkOPlcIW8P92KuGr5G418sweeIwhdh9dRmu4UPiEhKDkzw3nCl52fvEtqnWEAPOSQ/mZoHgR52H5uO1OV0wvfl5B5MCCdHmb";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlVIU8Qt6sHn6DCxAgG5a+NA5X2rZesxntXJjWjcmsWXcS/EqMaBJ4bP09Mg3YYJ8+skVTAlqC6LKoAaoD/BaAOUQNmhyTAlmyivoiXXP3R79cZNjOj3tHLVqhi7RACcS7O+H4QcP9h0/aHiu8RPHfhJL4Ua1EheJX645nBocEMqI/qUfRycxxoZ7BXZnqI1+UJNmJAidxBBEj7H9flz6bXmhxuIVzwWI3vY8zkA4bzbFlQ2FlCdRU7FTNqhygF/IJl6ej0dnLPkFE6ZXhz6pcKtLME4K3IASjAwTHmk9SQqDPpdHZfCDNpE5r4ZCvzuDOGpcETp8bsahyPkpsueSQwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://www.baidu.com";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/pay.html";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";

	//返回格式
    public static String format="json";
	
	// 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑/u

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

