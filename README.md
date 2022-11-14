
#                     <center>k2Pool-java-sdk 使用教程</center>

## 第一步：引入依赖

		<dependency>
		    <groupId>io.k2pool</groupId>
		    <artifactId>k2Pool-java-sdk</artifactId>
		    <version>1.6.4</version>
		</dependency>

## 第二步：application.yml 中加入如下配置：

		k2pool:
		  #域名
		  domain: http://127.0.0.1:8071
		  #用户唯一标识,由K2Pool提供
		  accessKey: 3e4434699e1347ed8245883f8c96f64f
		  #秘钥,用户自定义
		  aesKey: 51966658308f4b7d8eec93d67d4eec5b
		  #token过期时间,单位分钟，默认30分钟
		  tokenExpires: 30


## 第三步：获取 token 示例

		public class K2Test {

		    @Autowired
		    private K2Pool k2Pool;
	
		    @Test
		    public void tokenTest() {
		        Result<TokenVO> token = k2Pool.getToken();
		        System.out.println("token信息:{}");
		        log.info("token信息:{}", JSON.toJSONString(token));
		    }
	    }
<font color="red">K2Pool 客户端对象,方便用户调用平台 api 接口</font>

## 第四步： 初始化 token

	@Configuration
	@EnableScheduling
	public class UpdateTokenTask {
	    @Autowired
	    private K2Pool k2Pool;
	    /**
	     * 刷新时间需要比token过期时间小
	     */
	    @Scheduled(cron = "0 */25 * * * ?")
	    public void run() {
		Result<TokenVO> result = k2Pool.getToken();
		K2Pool.init(result.getData().getToken());
	    }
	}
<font color="red">推荐使用定时器，定时更新 token 并初始化</font>

## 第五步: 使用 K2Pool 调用接口

	示例(1)
          获取token,地址: http://ip:post/user-server/k2Pool/getToken
	      sdk调用:k2Pool.getToken()
	
    示例(2)
         获取全网数据:全网当前总算力、全网活跃矿工、32G/64G矿工封装Gas费用、32G/64G矿工质押成本,地址:http://ip:post/miner-server/k2Pool/network
         sdk调用：k2Pool.network()

<font color="red">结论：api地址最后一个斜杠后面的字符串即为相对应的方法名</font>


[api 文档][Wiki]

[Wiki]:https://github.com/baohj/k2Pool-java-sdk/wiki
