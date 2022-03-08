from google_images_download import google_images_download   #importing the library

response = google_images_download.googleimagesdownload()   #class instantiation

arguments = {"keywords":"박보영, 아이유, 윤승아, 민아(걸스데이), 한지민, \
안소희(원더걸스), 오연서, 한예슬, 이성경, 이효리, \
수지, 나연(트와이스), 예린(여자친구), 한승연(카라), 문채원, \
경리(나인뮤지스), 예지(ITZY), 한혜진(모델), 헤이즈, 지연(티아라),\
윤아(소녀시대), 이연희, 고아라, 문근영, 정유미(부산행 배우)\
","limit":50,"print_urls":True, "format": "jpg"}   #creating list of arguments

paths = response.download(arguments)   #passing the arguments to the function
print(paths)   #printing absolute paths of the downloaded images 