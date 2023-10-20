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

@app.route('/spelling', methods=['GET'])  # Sử dụng phương thức POST
def correct():
    try:
        print(data)
        data = request.get_json()  # Đọc dữ liệu JSON từ yêu cầu POST
        text = data.get("text")
        print(text)
        if not text or text == "" or len(text) < 10:
            print("Received nothing!", file=sys.stderr)
            response_data = {"error": f"Received short text '{text}'"}
        else:
            out = correctFunction(text, corrector)
            result = postprocessing_result(out)
            response_data = {"correction": result}
        return jsonify(response_data)  # Trả về dữ liệu JSON
    except Exception as e:
        print(str(e), file=sys.stderr)
        return jsonify({"error": "An error occurred"})








