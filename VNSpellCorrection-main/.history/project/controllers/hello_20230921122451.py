"""
    Example Controllers
"""

from project import app, corrector
import sys
from flask import render_template, redirect, url_for, request, jsonify
from utils.api_utils import correctFunction, postprocessing_result

#route index
@app.route('/', methods = ['GET'])
def index():
    data = {
        "title": "Hello World",
        "body": "Flask simple MVC"
    }
    return render_template('index.html', data = data)

@app.route('/spelling', methods=['POST'])  # Sử dụng phương thức POST
def correct():
    try:
        data = request.json  # Lấy dữ liệu JSON từ yêu cầu POST

        if not data or 'text' not in data:
            print("Received invalid data!", file=sys.stderr)
            return jsonify({"error": "Invalid data"}), 400  # Trả về lỗi nếu dữ liệu không hợp lệ

        text = data['text']
        
        if not text or text == "" or len(text) < 10:
            print("Received nothing!", file=sys.stderr)
            return jsonify({"error": f"Received short text '{text}'"}), 400  # Trả về lỗi nếu dữ liệu không hợp lệ

        # Xử lý dữ liệu ở đây và trả kết quả dưới dạng JSON
        out = correctFunction(text, corrector)
        result = postprocessing_result(out)
        return jsonify({"correction": result}), 200
    except Exception as e:
        print(str(e), file=sys.stderr)
        return jsonify({"error": "An error occurred"}), 500 
