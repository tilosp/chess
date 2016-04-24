package de.tilosp.chess.gui;

import de.tilosp.chess.icon.Icons;
import de.tilosp.chess.lib.*;
import de.tilosp.chess.localisation.Localisation;
import de.tilosp.chess.player.ComputerPlayer;
import de.tilosp.chess.player.LocalPlayer;
import de.tilosp.chess.player.Player;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class ChessboardGUI extends GUI {

    private static final Border BORDER_INSERTS = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    private static final Border BORDER_BEVEL_RAISED = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static final Border BORDER_BEVEL_LOWERED = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    private static final Border BORDER_SIDE_PANEL = BorderFactory.createCompoundBorder(BORDER_BEVEL_RAISED, BORDER_BEVEL_LOWERED);

    private static final Font FONT_LABEL = new Font(null, 0, 75);
    private static final Font FONT_LABEL_PLAYER = new Font(null, Font.BOLD, 45);
    private static final Font FONT_LABEL_EVALUATION = new Font(null, 0, 20);

    private ChessboardButton[][] boardButtons;
    private JButton[] promotionButtons;
    private JLabel[] topLabels;
    private JLabel[] leftLabels;
    private JLabel playerLabel;
    private JLabel timeLabel;
    private JLabel evaluationLabel;
    private JPanel chessboardPanel;
    private JPanel promotionPanel;

    private JMenuItem newMenuItem;
    private JCheckBoxMenuItem cheatCheckBoxMenuItem;

    private final Player[] players;
    private Chessboard chessboard;
    private int[] selected;

    public ChessboardGUI(Player player1, Player player2) {
        super();

        players = new Player[] { player1, player2 };
        players[0].init(PlayerColor.WHITE, this);
        players[1].init(PlayerColor.BLACK, this);

        if (players[0] instanceof ComputerPlayer) // compute first turn
            players[0].sendUpdate(chessboard);

        cheatCheckBoxMenuItem.setEnabled(players[0] instanceof LocalPlayer && players[1] instanceof LocalPlayer);
    }

    @Override
    void initGUI() {
        setTitle(Localisation.getString("chessboard.title"));
        panel.setLayout(new BorderLayout());
        JPanel chessboardMPanel = new JPanel();
        panel.add(chessboardMPanel, BorderLayout.CENTER);

        // initialise menu bar
        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);
        JMenu gameMenu = new JMenu(Localisation.getString("chessboard.menu_bar.game"));
        menu.add(gameMenu);
        gameMenu.add(newMenuItem = new JMenuItem(Localisation.getString("chessboard.menu_bar.game.new")));
        gameMenu.addSeparator();
        gameMenu.add(cheatCheckBoxMenuItem = new JCheckBoxMenuItem(Localisation.getString("chessboard.menu_bar.game.cheat_mode")));

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

        JPanel sideTopPanel = new JPanel();
        sideTopPanel.setLayout(new BoxLayout(sideTopPanel, BoxLayout.Y_AXIS));
        sidePanel.add(sideTopPanel, BorderLayout.PAGE_START);

        playerLabel = new JLabel();
        playerLabel.setFont(FONT_LABEL_PLAYER);
        playerLabel.setBorder(BORDER_INSERTS);
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sideTopPanel.add(playerLabel);

        evaluationLabel = new JLabel();
        evaluationLabel.setFont(FONT_LABEL_EVALUATION);
        evaluationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sideTopPanel.add(evaluationLabel);


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
        // menu bar listeners
        newMenuItem.addActionListener(e -> {
            // Open NewGameGUI Window
            new NewGameGUI().setVisible(true);
        });
        // chessboard listeners
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                final int fX = x, fY = y;
                boardButtons[x][y].addActionListener(e -> buttonPressed(fX, fY));
                boardButtons[x][y].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (cheatCheckBoxMenuItem.isSelected()) {
                            if (e.getButton() == MouseEvent.BUTTON2) { // middle click
                                chessboard = chessboard.cycleColor(fX, fY);
                                selected = null;
                                updateIcons();
                                updateBackground();
                            } else if (e.getButton() == MouseEvent.BUTTON3) { // right click
                                chessboard = chessboard.cycleChessPieceType(fX, fY);
                                selected = null;
                                updateIcons();
                                updateBackground();
                            }
                        }
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
                    for (Player p : players) p.sendUpdate(chessboard);
                    selected = null;
                    updateIcons();
                    updateBackground();
                    if (!chessboard.promotion)
                        updateGameState();
                }
            }
        }
    }

    private void promotionButtonPressed(int i) {
        if (chessboard.promotion && players[chessboard.playerColor.ordinal()] instanceof LocalPlayer) {
            chessboard = chessboard.promotion(ChessPieceType.POSITIONS_PROMOTION[i]);
            for (Player p : players) p.sendUpdate(chessboard);
            updateIcons();
            chessboardPanel.repaint();
            updateGameState();
        }
    }

    public void externalUpdate(Chessboard chessboard) {
        this.chessboard = chessboard;
        for (Player p : players) p.sendUpdate(chessboard);
        updateIcons();
        chessboardPanel.repaint();
        updateGameState();
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

        playerLabel.setText(Localisation.getString("player_color." + chessboard.playerColor.toString().toLowerCase()));

        int value = ChessComputer.computeValue(chessboard);
        evaluationLabel.setText((value < 0 ? "-" : (value > 0 ? "+" : "")) + (Math.abs(value) / 100) + "." + (Math.abs(value) % 100 / 10) + (Math.abs(value) % 10));
    }

    private void updateGameState() {
        if (chessboard.isDraw()) {
            JOptionPane.showMessageDialog(this, Localisation.getString("chessboard.message.draw"), Localisation.getString("chessboard.message.draw"), JOptionPane.PLAIN_MESSAGE);
        } else if (chessboard.isWin(PlayerColor.WHITE)) {
            JOptionPane.showMessageDialog(this, Localisation.getString("chessboard.message.white_won"), Localisation.getString("chessboard.message.white_won"), JOptionPane.PLAIN_MESSAGE);
        } else if (chessboard.isWin(PlayerColor.BLACK)) {
            JOptionPane.showMessageDialog(this, Localisation.getString("chessboard.message.black_won"), Localisation.getString("chessboard.message.black_won"), JOptionPane.PLAIN_MESSAGE);
        }
    }
}
