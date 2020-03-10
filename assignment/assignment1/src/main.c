#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <errno.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <fcntl.h>
#include <sys/sendfile.h>

#define MAX 300
#define PORT 8000
#define SA struct sockaddr 
#define ROOT "/home/windrain/UniqueWeb/assignment/assignment1/src"

void parseHeader(int sockfd, char *buff, int size);
void send_404(int sockfd);
void sendHeaders(int sockfd, const char *filename, char *type);
void getTypeName(char *filename, char *type);

int main(int argc, char *argv[]) {
	int listenfd, connfd;
	pid_t childpid;
	socklen_t clilen;
	struct sockaddr_in servaddr, cliaddr;
	char buff[MAX], path[MAX];


	if ( (listenfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
		printf("创建socket失败\n");
		return -1;
	}
	memset(&servaddr,0,sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_port = htons(PORT);
	servaddr.sin_addr.s_addr = INADDR_ANY;
	if ((bind(listenfd, (SA *) &servaddr, sizeof(servaddr))) < 0) {
		printf("绑定失败\n");
		return -1;
	}
	if ((listen(listenfd, 5)) < 0) {
		printf("监听失败\n");
		return -1;
	}
	printf("等待客户连接\n");
	for (;;) {
		clilen = sizeof(cliaddr);
		if ( (connfd = accept(listenfd, (SA *)&cliaddr, &clilen)) < 0) {
			printf("连接失败\n");
			return -1;
		}
		printf("一个客户连接\n");
		parseHeader(connfd, buff, sizeof(buff));
		memset(path, 0, sizeof(path));
		strcat(path, ROOT);//
		strcat(path, buff);
		printf("%s\n",path);
		if (path[strlen(path)-1] == '/') {
			strcat(path, "index.html");			
		}
		int filefd;
		if ( (filefd = open(path, O_RDONLY) )< 0 ) {
			printf("找不到文件\n");
			send_404(connfd);
		} else {
			struct stat file;
			fstat(filefd, &file);
			char type[50];
			getTypeName(path,type);
			sendHeaders(connfd,path,type);
			sendfile(connfd, filefd, 0, file.st_size);
			printf("发送了文件%s\n",path);
		}
		
	}
	return(0);
}

void parseHeader(int sockfd, char *buff, int size) {
	char path[MAX];
	char c;
	int x;
	memset(buff, 0, size);
	if ((x = recv(sockfd, buff, size, 0)) < 0) {
		printf("read fail\n");
	}
	printf("%s\n",buff);
	int i = 0, j =0;
	for (; buff[i] != ' '; i++);
	i++;
	for (; buff[i] != ' ' && buff[i] != '\r';) {
		path[j++] = buff[i++];
	}
	printf("%s\n",path);
	path[j] = '\0';
	printf("%s\n",path);
	memset(buff, 0, size);
	strcpy(buff, path);
}

void send_404(int sockfd) {
	char buff[MAX] = "HTTP/1.0 404 NOT FOUND\r\nContent-Type: text/html\r\n\r\n"\
	"<!DOCTYPE html><html><head><title>页面不存在</title></head>"\
	"<body><h1>页面不存在</h1></body>";
	send(sockfd, buff, strlen(buff), 0);
	printf("send 404\n");
}

void sendHeaders(int sockfd, const char *filename, char *type)
{
    char buff[1024];
    strcpy(buff, "HTTP/1.0 200 OK\r\n");
    send(sockfd, buff, strlen(buff), 0);
	sprintf(buff, "Content-Type: %s\r\n\r\n",type);
    send(sockfd, buff, strlen(buff), 0);
    
}

void getTypeName(char *filename, char *type) {
	int i = strlen(filename) - 1, j = 0;
	for ( ; filename[i] != '.'; i--);
	for ( ; i <=strlen(filename);){
		type[j++] = filename[i++];
	}
	if (strcmp(".txt",type) == 0) {
		sprintf(type,"text/plain");
	} else if (strcmp(".jpg",type) == 0) {
		sprintf(type,"image/jpeg");
	} else if (strcmp(".png",type) == 0){
		sprintf(type,"image/png");
	} else if (strcmp(".html",type) == 0){
		sprintf(type,"text/html");
	} else if (strcmp(".zip",type) == 0){
		sprintf(type,"application/x-zip-compressed");
	} 
}

