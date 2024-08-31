Weather Temperature Prediction System
Overview
This project aims to predict the maximum and minimum temperatures for the next five days based on input features such as rain and start date. The machine learning models are built using TensorFlow and saved in TensorFlow Lite format for integration into Android applications.

Requirements
Python 3.x
TensorFlow
Scikit-learn
Pandas
A high-resolution dataset of weather data
Workflow
Data Preparation:

Load and preprocess the weather dataset.
Handle missing values and create new features.
Model Training:

Train models to predict maximum and minimum temperatures using a neural network.
Evaluate models and ensure they meet accuracy requirements.
Model Conversion:

Save the trained models in .h5 format.
Convert models to TensorFlow Lite format (.tflite).
Android Integration:

Use the .tflite models in Android Studio for real-time temperature predictions.
How to Use the Models in Android Studio
Download Files:

Ensure you have the following .tflite model files generated from accuracy.py:
model_max.tflite
model_min.tflite
Add Models to Your Android Project:

Place the .tflite model files into the assets directory of your Android project.
Integrate TensorFlow Lite Models:

In your Android project, configure TensorFlow Lite to use the models.
Load the models and use them for inference in your application.
