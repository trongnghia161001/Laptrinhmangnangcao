from project import app, corrector
import sys
from flask import render_template, redirect, url_for, request, jsonify
from utils.api_utils import correctFunction, postprocessing_result
import threading
from concurrent.futures import ThreadPoolExecutor
from tqdm import tqdm
import time

# Hàm xử lý đoạn text và trả về kết quả
def process_text(text):
    try:
        if not text or text == "" or len(text) < 10:
            response_data = {"error": f"Received short text '{text}'"}
        else:
            # Thực hiện xử lý của bạn ở đây
            # Ví dụ: out = correctFunction(text, corrector)
            # result = postprocessing_result(out)
            out = correctFunction(text, corrector)
            result = postprocessing_result(out)
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




def process_text(text, progress_bar):
    try:
        if not text or text == "" or len(text) < 10:
            response_data = {"error": f"Received short text '{text}'"}
        else:
            # Sử dụng tqdm để hiển thị thanh tiến trình dưới dạng phần trăm (%)
            for _ in progress_bar:
                time.sleep(1)
                progress_bar.update(10)  # Sử dụng update(10) để hiển thị % hoàn thành
            response_data = {"correction": "Processed successfully"}
        return response_data
    except Exception as e:
        return {"error": "An error occurred"}

@app.route('/spelling', methods=['POST'])
def correct():
    try:
        text = request.data.decode("utf-8")

        # Sử dụng tqdm để tạo thanh tiến trình dưới dạng phần trăm (%)
        progress_bar = tqdm(total=100, desc="Processing", ncols=100, ascii=False, unit='%')

        # Sử dụng ThreadPoolExecutor để xử lý yêu cầu đồng thời
        with ThreadPoolExecutor() as executor:
            future = executor.submit(process_text, text, progress_bar)

        # Đợi tiến trình con hoàn thành
        while not future.done():
            pass

        progress_bar.close()  # Đóng thanh tiến trình khi hoàn thành

        response_data = future.result()

        return jsonify(response_data)
    except Exception as e:
        return jsonify({"error": "An error occurred"})

if __name__ == '__main__':
    app.run(host="localhost", port=8080, debug=True)







