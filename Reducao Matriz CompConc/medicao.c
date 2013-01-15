#include <sys/time.h>
#include <stdlib.h>
struct timeval startTime, endTime;
unsigned int paraContagem()
{	
	unsigned int totalSecs;
	
	gettimeofday(&endTime, NULL);
	totalSecs = (unsigned long long) (endTime.tv_sec - startTime.tv_sec) * 1000000 +
               (unsigned long long) (endTime.tv_usec - startTime.tv_usec);
			   
    return totalSecs;
}
void comecaContagem()
{
	gettimeofday(&startTime, NULL);
}
