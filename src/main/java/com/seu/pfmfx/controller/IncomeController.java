package com.seu.pfmfx.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class IncomeController {

        @FXML
        private Button btnAddIncome;

        @FXML
        private ComboBox<?> cbCategory;

        @FXML
        private TableColumn<?, ?> colAmount;

        @FXML
        private TableColumn<?, ?> colCategory;

        @FXML
        private TableColumn<?, ?> colDate;

        @FXML
        private TableColumn<?, ?> colDescription;

        @FXML
        private DatePicker dpDate;

        @FXML
        private Label incomeList;

        @FXML
        private TableView<?> incomeTable;

        @FXML
        private TextField txtAmount;

        @FXML
        private TextArea txtDescription;

        @FXML
        void btnAddIncome(ActionEvent event) {
        }
        }
