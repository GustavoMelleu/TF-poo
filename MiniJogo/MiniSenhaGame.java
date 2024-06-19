
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MiniSenhaGame extends JFrame {
    private final JButton[][] colorSquares = new JButton[8][4];
    private final JPanel[][] indicatorSquares = new JPanel[8][4];
    private Color[] botSequence = new Color[4];
    private int currentRow = 0;

    public MiniSenhaGame() {
        setTitle("Jogo da Mini Senha");
        setSize(400, 800); // Ajuste o tamanho conforme necessário
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Adiciona o tabuleiro ao painel principal
        mainPanel.add(createBoard(), BorderLayout.CENTER);
        mainPanel.add(createValidationButton(), BorderLayout.SOUTH);

        add(mainPanel);
        generateBotSequence();
    }

    private JPanel createBoard() {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 1)); // 8 divisórias

        for (int i = 0; i < 8; i++) {
            boardPanel.add(createRow(i));
        }

        return boardPanel;
    }

    private JPanel createRow(int rowIndex) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new GridLayout(1, 2)); // Uma linha para quadrados e outra para indicadores

        // Painel dos quadrados de cores
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new GridLayout(1, 4)); // 4 quadrados

        for (int i = 0; i < 4; i++) {
            JButton square = createColorSquare(rowIndex, i);
            colorSquares[rowIndex][i] = square;
            colorPanel.add(square);
        }

        // Painel dos indicadores
        JPanel indicatorPanel = new JPanel();
        indicatorPanel.setLayout(new GridLayout(2, 2)); // 4 mini quadrados

        for (int i = 0; i < 4; i++) {
            JPanel indicator = createIndicatorSquare();
            indicatorSquares[rowIndex][i] = indicator;
            indicatorPanel.add(indicator);
        }

        rowPanel.add(colorPanel);
        rowPanel.add(indicatorPanel);

        return rowPanel;
    }

    private JButton createColorSquare(int rowIndex, int colIndex) {
        JButton square = new JButton();
        square.setBackground(Color.GRAY); // Cor inicial
        square.setPreferredSize(new Dimension(50, 50));
        square.setEnabled(rowIndex == currentRow); // Ativa apenas a linha atual
        // Adicionar ação para trocar cor
        square.addActionListener(e -> changeColor(square));
        return square;
    }

    private JPanel createIndicatorSquare() {
        JPanel indicator = new JPanel();
        indicator.setBackground(Color.WHITE); // Cor inicial
        indicator.setPreferredSize(new Dimension(25, 25));
        return indicator;
    }

    private void changeColor(JButton button) {
        // Lógica para trocar a cor do quadrado
        Color currentColor = button.getBackground();
        Color newColor = GameColor.getNextColor(currentColor);
        button.setBackground(newColor);
    }

    private JButton createValidationButton() {
        JButton validateButton = new JButton("Validar");
        // Adicionar ação para validar as cores
        validateButton.addActionListener(e -> validateColors());
        return validateButton;
    }

    private void validateColors() {
        if (currentRow >= 8) {
            JOptionPane.showMessageDialog(this, "Você alcançou o número máximo de tentativas!");
            return;
        }

        int correctPosition = 0;
        int correctColor = 0;
        boolean[] botUsed = new boolean[4];
        boolean[] playerUsed = new boolean[4];

        // Verifica cores na posição correta
        for (int i = 0; i < 4; i++) {
            Color playerColor = colorSquares[currentRow][i].getBackground();
            if (playerColor.equals(botSequence[i])) {
                correctPosition++;
                botUsed[i] = true;
                playerUsed[i] = true;
            }
        }

        // Verifica cores corretas fora da posição
        for (int i = 0; i < 4; i++) {
            if (!playerUsed[i]) {
                for (int j = 0; j < 4; j++) {
                    if (!botUsed[j] && colorSquares[currentRow][i].getBackground().equals(botSequence[j])) {
                        correctColor++;
                        botUsed[j] = true;
                        break;
                    }
                }
            }
        }

        // Atualiza os indicadores
        for (int i = 0; i < correctPosition; i++) {
            indicatorSquares[currentRow][i].setBackground(Color.BLACK);
        }
        for (int i = correctPosition; i < correctPosition + correctColor; i++) {
            indicatorSquares[currentRow][i].setBackground(Color.GRAY);
        }

        // Verifica se o jogador ganhou
        if (correctPosition == 4) {
            JOptionPane.showMessageDialog(this, "Parabéns! Você acertou a sequência!");
        } else {
            currentRow++;
            if (currentRow >= 8) {
                JOptionPane.showMessageDialog(this, "Fim de jogo! Você não acertou a sequência.");
            } else {
                enableCurrentRow();
            }
        }
    }

    private void enableCurrentRow() {
        for (int i = 0; i < 4; i++) {
            colorSquares[currentRow][i].setEnabled(true);
        }
    }

    private void generateBotSequence() {
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            botSequence[i] = GameColor.COLORS[rand.nextInt(GameColor.COLORS.length)];
        }
        // Descomente a linha abaixo para ver a sequência gerada (para fins de teste)
        // System.out.println(java.util.Arrays.toString(botSequence));
    }

}


