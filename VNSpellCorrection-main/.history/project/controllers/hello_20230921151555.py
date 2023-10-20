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
        # Đọc dữ liệu text từ yêu cầu POST
        text = request.data.decode("utf-8")

        if not text or text == "" or len(text) < 10:
            response_data = {"error": f"Received short text '{text}'"}
        else:
            out = correctFunction(text, corrector)
            result = postprocessing_result(out)
            response_data = {"correction": result}
        return jsonify(response_data)  # Trả về dữ liệu JSON
    except Exception as e:
        return jsonify({"error": "An error occurred"})







