"""
    Example Controllers
"""

# from project import app, corrector
# import sys
# from flask import render_template, redirect, url_for, request, jsonify
# from utils.api_utils import correctFunction, postprocessing_result
# 
# 
# #route index
# @app.route('/', methods = ['GET'])
# def index():
#     data = {
#         "title": "Hello World",
#         "body": "Flask simple MVC"
#     }
#     return render_template('index.html', data = data)
# 
# @app.route('/spelling', methods=['POST'])  # Sử dụng phương thức POST
# def correct():
#     try:
#         # Đọc dữ liệu text từ yêu cầu POST
#         text = request.data.decode("utf-8")
# 
#         if not text or text == "" or len(text) < 10:
#             response_data = {"error": f"Received short text '{text}'"}
#         else:
#             out = correctFunction(text, corrector)
#             result = postprocessing_result(out)
#             response_data = {"correction": result}
#         return jsonify(response_data)  # Trả về dữ liệu JSON
#     except Exception as e:
#         return jsonify({"error": "An error occurred"})


from flask import Flask, request, jsonify
import threading
import concurrent.futures

app = Flask(__name__)

# Hàm xử lý đoạn text và trả về kết quả
def process_text(text):
    try:
        if not text or text == "" or len(text) < 10:
            response_data = {"error": f"Received short text '{text}'"}
        else:
            # Thực hiện xử lý của bạn ở đây
            # Ví dụ: out = correctFunction(text, corrector)
            # result = postprocessing_result(out)
            result = "Processed result"  # Thay bằng kết quả thực tế của bạn
            response_data = {"correction": result}
        return response_data
    except Exception as e:
        return {"error": "An error occurred"}

# Định nghĩa endpoint /spelling cho phương thức POST
@app.route('/spelling', methods=['POST'])
def correct():
    try:
        # Đọc dữ liệu text từ yêu cầu POST
        text = request.data.decode("utf-8")

        # Sử dụng một pool thread để xử lý yêu cầu đồng thời
        with concurrent.futures.ThreadPoolExecutor() as executor:
            future = executor.submit(process_text, text)
            response_data = future.result()

        return jsonify(response_data)
    except Exception as e:
        return jsonify({"error": "An error occurred"})








