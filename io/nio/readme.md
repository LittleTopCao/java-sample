几个概念
1. Channel : 通道，代表一个与实体（硬件设备、文件、网络套接字或程序组件）得链接，可以 读或写
   FileChannel：读写文件
   DatagramChannel: UDP协议网络通信
   SocketChannel：TCP协议网络通信
   ServerSocketChannel：监听TCP连接
   
2. Buffer ： 缓冲区，用于从通道中读取数据或写入数据
   和 C 语言中得数组类似
   有三个概念
       1. capacity （总容量），缓冲区总容量
       2. position （指针当前位置），当前读到的位置
       3. limit （读/写边界位置），数据填充了多少
   
3. Selector : 选择器，帮我们监听各个通道的状态


这有点像是 IO多路复用的 模型