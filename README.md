# uploadFileiMx
iMatrix File Upload Serivce

# sample script to call service with fileupload
javac IMatrixRestApiCaller.java 

java IMatrixRestApiCaller "UPLOAD_FILE" "localhost:8090" "ref123" "pname" "filedesc" "vtype" "orderno" "vonu" "comguid" "guid df dfs 23" "C:\Users\runa3\Desktop\test\TestPDF-12.pdf"

# log file path
C:\var\log\imatrix\restapi
C:\var\log\imatrix\imFileUploadApp