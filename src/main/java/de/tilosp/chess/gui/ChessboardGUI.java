package de.tilosp.chess.gui;

import de.tilosp.chess.icon.Icons;
import de.tilosp.chess.lib.ChessPiece;
import de.tilosp.chess.lib.ChessPieceType;
import de.tilosp.chess.lib.Chessboard;
import de.tilosp.chess.localisation.Localisation;
import de.tilosp.chess.player.LocalPlayer;
import de.tilosp.chess.player.Player;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChessboardGUI extends GUI {

    private static final Border BORDER_INSERTS = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    private static final Border BORDER_BEVEL_RAISED = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static final Border BORDER_BEVEL_LOWERED = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    private static final Border BORDER_SIDE_PANEL = BorderFactory.createCompoundBorder(BORDER_BEVEL_RAISED, BORDER_BEVEL_LOWERED);

    private static final Font FONT_LABEL = new Font(null, 0, 75);
    private static final Font FONT_LABEL_TOP = new Font(null, Font.BOLD, 45);

    private ChessboardButton[][] boardButtons;
    private JButton[] promotionButtons;
    private JLabel[] topLabels;
    private JLabel[] leftLabels;
    private JLabel topLabel;
    private JPanel chessboardPanel;
    private JPanel promotionPanel;

    private final Player[] players;
    private Chessboard chessboard;
    private int[] selected;

    public ChessboardGUI(Player player1, Player player2) {
        super();
        players = new Player[] { player1, player2 };
    }

    @Override
    void initGUI() {
        setTitle(Localisation.getString("chessboard.title"));
        panel.setLayout(new BorderLayout());
        JPanel chessboardMPanel = new JPanel();
        panel.add(chessboardMPanel, BorderLayout.CENTER);

        chessboard = new Chessboard();

        // initialise chessboard
        chessboardPanel = new JPanel(new GridLayout(9, 9));
        chessboardMPanel.add(chessboardPanel);
        chessboardMPanel.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                int size =  Math.min(chessboardMPanel.getWidth(), chessboardMPanel.getHeight());
                chessboardPanel.setPreferredSize(new Dimension(size, size));
                chessboardMPanel.revalidate();
            }
        });

        boardButtons = new ChessboardButton[8][8];
        promotionButtons = new JButton[4];
        topLabels = new JLabel[8];
        leftLabels = new JLabel[8];

        chessboardPanel.add(new JLabel());
        for (int i = 0; i < 8; i++)
            chessboardPanel.add(topLabels[i] = newLabel(Character.toString((char) (0x61 + i))));
        for (int y = 0; y < 8; y++) {
            chessboardPanel.add(leftLabels[y] = newLabel(Integer.toString(8 - y)));
            for (int x = 0; x < 8; x++)
                chessboardPanel.add(boardButtons[x][y] = new ChessboardButton(((x ^ y) & 1) == 0));
        }

        // initialise side panel
        JPanel sidePanelO = new JPanel(new BorderLayout());
        panel.add(sidePanelO, BorderLayout.LINE_END);
        sidePanelO.setBorder(BORDER_INSERTS);

        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanelO.add(sidePanel, BorderLayout.CENTER);
        sidePanel.setBorder(BORDER_SIDE_PANEL);


        sidePanel.add(Box.createVerticalGlue(), BorderLayout.CENTER);
        topLabel = new JLabel();
        topLabel.setFont(FONT_LABEL_TOP);
        topLabel.setBorder(BORDER_INSERTS);
        topLabel.setHorizontalAlignment(SwingConstants.CENTER);

        sidePanel.add(topLabel, BorderLayout.PAGE_START);

        // add promotion buttons
        promotionPanel = new JPanel(new GridLayout(2, 2));
        promotionPanel.setBorder(BORDER_INSERTS);
        for (int i = 0; i < 4; i++) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(100, 100));
            button.setContentAreaFilled(false);
            button.setBorder(null);
            promotionPanel.add(promotionButtons[i] = button);
        }
        sidePanel.add(promotionPanel, BorderLayout.PAGE_END);

        // setup background
        updateBackground();

        // setup icons
        updateIcons();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    void initListeners() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                final int fX = x, fY = y;
                boardButtons[x][y].addActionListener(e -> buttonPressed(fX, fY));
                boardButtons[x][y].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        setLabels(fX, fY);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        resetLabels(fX, fY);
                    }
                });
            }
        }
        for (int i = 0; i < 4; i++) {
            final int fI = i;
            promotionButtons[i].addActionListener(e -> promotionButtonPressed(fI));
        }
    }

    private void resetLabels(int x, int y) {
        Font font = topLabels[x].getFont();
        topLabels[x].setFont(new Font(font.getName(), font.getStyle() & ~Font.BOLD, font.getSize()));
        font = leftLabels[y].getFont();
        leftLabels[y].setFont(new Font(font.getName(), font.getStyle() & ~Font.BOLD, font.getSize()));
    }

    private void setLabels(int x, int y) {
        Font font = topLabels[x].getFont();
        topLabels[x].setFont(new Font(font.getName(), font.getStyle() | Font.BOLD, font.getSize()));
        font = leftLabels[y].getFont();
        leftLabels[y].setFont(new Font(font.getName(), font.getStyle() | Font.BOLD, font.getSize()));
    }

    private void buttonPressed(int x, int y) {
        if (!chessboard.promotion) {
            if (chessboard.getChessPiece(x, y) != null && chessboard.getChessPiece(x, y).playerColor == chessboard.playerColor && players[chessboard.playerColor.ordinal()] instanceof LocalPlayer && !chessboard.getPossibleMoves(x, y).isEmpty()) {
                selected = new int[] { x, y };
                updateBackground();
            } else if (selected != null) {
                Chessboard move = chessboard.checkAndMove(selected, new int[] { x, y });
                if (move != null) {
                    chessboard = move;
                    selected = null;
                    updateIcons();
                    updateBackground();
                }
            }
        }
    }

    private void promotionButtonPressed(int i) {
        if (chessboard.promotion && players[chessboard.playerColor.ordinal()] instanceof LocalPlayer) {
            chessboard = chessboard.promotion(ChessPieceType.POSITIONS_PROMOTION[i]);
            updateIcons();
            chessboardPanel.repaint();
        }
    }

    private void updateBackground() {
        for (int y = 0; y < 8; y++)
            for (int x = 0; x < 8; x++)
                boardButtons[x][y].iconReset();
        if (selected != null) {
            boardButtons[selected[0]][selected[1]].iconSelected();
            for (int[] m : chessboard.getPossibleMoves(selected[0], selected[1]))
                boardButtons[m[0]][m[1]].iconShow();
        }
        chessboardPanel.repaint();
    }

    private JLabel newLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(FONT_LABEL);
        label.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                Font font = label.getFont();
                label.setFont(new Font(font.getName(), font.getStyle(), (int) (label.getSize().height * 0.8)));
            }
        });
        return label;
    }

    private void updateIcons() {
        // update chessboard icons
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                ChessPiece chessPiece = chessboard.getChessPiece(x, y);
                boardButtons[x][y].setIcon(chessPiece != null ? Icons.getIcon(chessPiece.playerColor, chessPiece.chessPieceType) : null);
            }
        }

        // update promotion icons
        for (int i = 0; i < 4; i++)
            promotionButtons[i].setIcon(chessboard.promotion && players[chessboard.playerColor.ordinal()] instanceof LocalPlayer ? Icons.getIcon(chessboard.playerColor, ChessPieceType.POSITIONS_PROMOTION[i]) : null);

        topLabel.setText(Localisation.getString("player_color." + chessboard.playerColor.toString().toLowerCase()));
    }
}
