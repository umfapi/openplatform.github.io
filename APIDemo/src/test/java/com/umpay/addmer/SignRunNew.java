package com.umpay.addmer;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.umpay.util.CertUtils;
import com.umpay.util.SignTools;


public class SignRunNew {
	// public static final String DEV_9996__PRIVATE_Key="D:/test.key.p8";
	// public static final String DEV_9996__PRIVATE_Key="D:/41100668_.key.p8";
//	 public static final String DEV_9996__PRIVATE_Key="D:/41509208_.key.p8";
	public static final String DEV_9996__PRIVATE_Key = "D:/testSpay.key.p8";

	public static void main(String[] args) {
		try {
			TreeMap<String, Object> reqMap = build3();

			// 获取原请求签名
			reqMap.remove("signature");
			String signStr = new StringBuilder(reqMap.toString().replace(", ", "&").replace("{", "").replace("}", ""))
					.toString();

			// 验签
			System.out.println("【待签名明文串】" + signStr);
			String sign = CertUtils.sign(signStr, CertUtils.getPrivateKey(DEV_9996__PRIVATE_Key), "UTF-8");
			System.out.println("【签名密文串】" + sign);
			reqMap.put("signature", sign);
			String pl = JSON.toJSONString(reqMap);
			System.out.println("【最终报文串】");
			System.out.println(pl);

			String vstr="{\"respCode\":\"00\",\"respMsg\":\"处理成功\",\"signature\":\"h8StdCJED0WqZa5R61qP9J1PZHAg8HZQIfXc1eVsEC3Tt9bYlV9mvUIZo19WvtolB7bTPrVllQf8+vm6IZaPWNLloMGXoGKgL3OqIJ2sLeJGC8F0pw/i6OLXP0sO7+GL6LP4SpM7VKr83bRUKGffAQErzATgaMHEnKMzNkwLYyY=\",\"platDate\":\"20190729164955\",\"orderNo\":\"1564390193636\",\"transactionId\":\"2019072916490000000044\",\"txnAmt\":\"1\",\"qrCode\":\"https://qr.95516.com/00010000/01110946678083296059512835211868\"}";
			boolean flag = doCheckSign(vstr);
			System.out.println(flag);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static TreeMap<String, Object> build1() {
		TreeMap<String, Object> reqMap = new TreeMap<String, Object>();
		reqMap.put("funCode", "ALIVE");
		reqMap.put("rpid", "123456789");
		// reqMap.put("acqSpId","S145871705");
		// reqMap.put("acqMerId","9996");
		reqMap.put("acqSpId", "Y471790403");
		reqMap.put("acqMerId", "41509208");
//		reqMap.put("orderNo", "1564035552259");// zfb
		// reqMap.put("orderNo","ad1133213d4d");//yl
//		 reqMap.put("orderNo","a12sa1sdad4d");//wx
		 reqMap.put("orderNo","aas12sadasd33sd");//wx
//		 reqMap.put("orderNo","a4421123sd332");//wx
//		 reqMap.put("orderNo","a1dqasda231111dsd");//ymf
		return reqMap;
	}

	public static final String certFilePath = "D:/";
	public static final String serverCertFileName = "testUmpay.cert.crt";

	@SuppressWarnings("unchecked")
	public static boolean doCheckSign(String object) {
		Map<String, Object> treeMap = JSON.parseObject(JSON.toJSONString(JSON.parse(object)), TreeMap.class);
		// 【响应的签名】
		String signKey = (String) treeMap.get("signature");
		treeMap.remove("signature");
		// 【待签明文串】--去除响应签名后获取待签明文串
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Object> entry : treeMap.entrySet()) {
			if (null != treeMap.get(entry.getKey()) && !"".equals(treeMap.get(entry.getKey()))) {
				sb.append(treeMap.get(entry.getKey())).append("|");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		String befSgin = sb.toString();
		System.out.println("拼装的验签明文串为：" + befSgin);
		//【验签】
		boolean sign = false;
		try {
			sign = SignTools.doCheckSign(certFilePath, serverCertFileName, befSgin, signKey);
		} catch (Exception e) {
			System.out.println("验签异常");
		}
		System.out.println("验签的结果为：" + sign);
		//【 验签结果】
		return sign;
	}

	

	public static TreeMap<String, Object> build2() {
		TreeMap<String, Object> reqMap = new TreeMap<String, Object>();
		// reqMap.put("funCode","ALIVE");
		// reqMap.put("rpid","123456789");
		reqMap.put("orderNo", "1564035552259");
		reqMap.put("orderTime", "20190725141912");
		reqMap.put("orderType", "alipay");
		reqMap.put("txnAmt", "1");

		reqMap.put("backUrl", "http://www.baidu.com");
		reqMap.put("goodsInfo", "京东生鲜食品");
		reqMap.put("paymentValidTime", "600");

		reqMap.put("acqSpId", "Y471790403");
		reqMap.put("acqMerId", "41509208");
		// reqMap.put("acqSpId","S145871705");
		// reqMap.put("acqMerId","68001013");

		return reqMap;
	}
	public static TreeMap<String, Object> build3() {
		//{acqMerId=41509208, acqSpId=Y471790403, backUrl=http://www.baidu.com, 
		//goodsInfo=京东生鲜食品, orderNo=1564390193636, orderTime=20190729164953, orderType=unionpay, 
		//paymentValidTime=600, signature=fzFtnUnJnYAdrWr2sJeg7kCUHlFexrV4X6yXPR2B6gbm5SpgtqWm0J8Q2eNDmFjS2blgFJ8wKn7IKiP02oCgez77/tIl+UNE4fkhEKVgYXDiQgvEP/CNhUncZEVyhpBJM/jl7d6puziD+afBoqYW+TAds8vck5qvu42QrPukvsM=, 
		//txnAmt=1
		TreeMap<String, Object> reqMap = new TreeMap<String, Object>();
		// reqMap.put("funCode","ALIVE");
		// reqMap.put("rpid","123456789");
		reqMap.put("orderNo", "1564390193636");
		reqMap.put("orderTime", "20190729164953");
		reqMap.put("orderType", "unionpay");
		reqMap.put("txnAmt", "1");
		
		reqMap.put("backUrl", "http://www.baidu.com");
		reqMap.put("goodsInfo", "京东生鲜食品");
		reqMap.put("paymentValidTime", "600");
		
		reqMap.put("acqSpId", "Y471790403");
		reqMap.put("acqMerId", "41509208");
		// reqMap.put("acqSpId","S145871705");
		// reqMap.put("acqMerId","68001013");
		
		return reqMap;
	}
}