package com.nseaf.mycalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvNumber;
    private TextView tvDetails;
    private double memoryValue = 0;
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String operator = "";
    private boolean isNewOp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNumber = findViewById(R.id.tv_number);
        tvDetails = findViewById(R.id.tv_details);

        setNumberButtonListeners();
        setOperationButtonListeners();
        setMemoryButtonListeners();
    }

    private void setNumberButtonListeners() {
        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (isNewOp) {
                    tvNumber.setText(button.getText());
                    isNewOp = false;
                } else {
                    tvNumber.append(button.getText().toString());
                }
                tvDetails.append(button.getText().toString());
            }
        };

        int[] numberButtonIds = {R.id.b_0, R.id.b_1, R.id.b_2, R.id.b_3, R.id.b_4, R.id.b_5, R.id.b_6, R.id.b_7, R.id.b_8, R.id.b_9};
        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(numberClickListener);
        }
    }

    private void setOperationButtonListeners() {
        View.OnClickListener operationClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                operator = button.getText().toString();
                firstNumber = Double.parseDouble(tvNumber.getText().toString());
                isNewOp = true;
                tvDetails.append(" " + operator + " ");
                tvNumber.setText(""); // Clear tvNumber to start showing the second number
            }
        };

        int[] operationButtonIds = {R.id.b_addition, R.id.b_subtract, R.id.b_multiply, R.id.b_div};
        for (int id : operationButtonIds) {
            findViewById(id).setOnClickListener(operationClickListener);
        }

        findViewById(R.id.b_equal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvNumber.getText().toString().isEmpty()) {
                    secondNumber = Double.parseDouble(tvNumber.getText().toString());
                    double result = 0;
                    switch (operator) {
                        case "+":
                            result = firstNumber + secondNumber;
                            break;
                        case "-":
                            result = firstNumber - secondNumber;
                            break;
                        case "×":
                            result = firstNumber * secondNumber;
                            break;
                        case "÷":
                            if (secondNumber != 0) {
                                result = firstNumber / secondNumber;
                            } else {
                                tvNumber.setText("Error");
                                tvDetails.append(" Error");
                                return;
                            }
                            break;
                    }
                    tvNumber.setText(String.valueOf(result));
                    tvDetails.append(" = " + result);
                    isNewOp = true;
                }
            }
        });

        findViewById(R.id.b_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvNumber.setText("0");
                tvDetails.setText("");
                firstNumber = 0;
                secondNumber = 0;
                operator = "";
                isNewOp = true;
            }
        });

        findViewById(R.id.b_radix_point).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNewOp) {
                    tvNumber.setText("0.");
                    tvDetails.append("0.");
                    isNewOp = false;
                } else if (!tvNumber.getText().toString().contains(".")) {
                    tvNumber.append(".");
                    tvDetails.append(".");
                }
            }
        });
    }

    private void setMemoryButtonListeners() {
        findViewById(R.id.b_m_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoryValue += Double.parseDouble(tvNumber.getText().toString());
                tvDetails.setText("Memory: " + memoryValue);
            }
        });

        findViewById(R.id.b_m_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoryValue -= Double.parseDouble(tvNumber.getText().toString());
                tvDetails.setText("Memory: " + memoryValue);
            }
        });

        findViewById(R.id.b_m_r).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvNumber.setText(String.valueOf(memoryValue));
            }
        });

        findViewById(R.id.b_m_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoryValue = 0;
                tvDetails.setText("Memory: 0");
            }
        });
    }

    public void percentClicked(View view) {
        double number = Double.parseDouble(tvNumber.getText().toString());
        double result = number / 100;
        tvNumber.setText(String.valueOf(result));
        tvDetails.setText(tvDetails.getText().toString() + " " + number + "% = " + result);
    }
}
