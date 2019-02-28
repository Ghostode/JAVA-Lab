平台：macOS 10.13.4
数据库：mysql 8.0.11

成功创建数据库和表项后，打开源代码文件 ConnectDB.java 修改连接数据库的相应参数（如数据库用户名和密码）。

修改完成后，打开终端在实验2的文件夹（lab2）下，输入如下命令编译：
javac -d bin src/*.java

输入如下命令启动程序：
java -cp bin:lib/mysql-connector-java-5.1.46-bin.jar MainWindow

