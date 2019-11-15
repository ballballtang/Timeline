# Timeline
前端：vue.js
后端：springboot
数据库：mysql
使用mokito进行单元测试

前端：
更新按键旁边的数字是实时更新的
点击更新按钮后在顶部插入最新的消息
滑动条拉到底自动加载消息
当所有消息（除了更新的消息）加载完之后会显示“没有更多了”

后端：
因为不要求做一个完整的社交软件，所以没有登录，添加消息，上传图片等接口
测试更新接口的时候是直接在数据库里添加消息
接口有4个：获取更新的消息，获取初始的消息，获取目前消息个数，根据ID查找消息

效果展示：
![Image text](https://https://raw.githubusercontent.com/ballballtang/Timeline/master/assets/frontend.jpg)
 
