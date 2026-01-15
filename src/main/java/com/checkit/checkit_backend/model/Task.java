package com.checkit.checkit_backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_order")
    private int taskOrder; // [cite: 104]

    @Column(nullable = false)
    private String name; // [cite: 105] (era 'title' nel tuo vecchio codice)

    @Column(nullable = false)
    private String type; // [cite: 106] (es. "QR", "NFC", "TEXT")

    // Soluzioni (possono essere nulle a seconda del tipo)
    @Column(name = "qr_answer")
    private String qrAnswer; // [cite: 107]

    @Column(name = "nfc_answer")
    private String nfcAnswer; // [cite: 108]

    @Column(name = "text_answer")
    private String textAnswer; // [cite: 109]

    // Relazione: Molte attività appartengono a Una Sfida
    @ManyToOne
    @JoinColumn(name = "challenge_id", nullable = false) // La FK per la sfida
    private Challenge challenge;

    //
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Clue> clues;
    


    // NOTA: 'completed' non è qui!
    // Come da diagramma [cite: 118-121], 'TaskCompletion' è una tabella separata 
    // che lega User e Task. La logica di "completamento" sarà nel Service.

    // Costruttori, Getters e Setters
    public Task() {}

    // Getters e Setters (aggiornati)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getTaskOrder() { return taskOrder; }
    public void setTaskOrder(int taskOrder) { this.taskOrder = taskOrder; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getQrAnswer() { return qrAnswer; }
    public void setQrAnswer(String qrAnswer) { this.qrAnswer = qrAnswer; }

    public String getNfcAnswer() { return nfcAnswer; }
    public void setNfcAnswer(String nfcAnswer) { this.nfcAnswer = nfcAnswer; }

    public String getTextAnswer() { return textAnswer; }
    public void setTextAnswer(String textAnswer) { this.textAnswer = textAnswer; }

    public Challenge getChallenge() { return challenge; }
    public void setChallenge(Challenge challenge) { this.challenge = challenge; }

    public List<Clue> getClues() { return clues; }
    public void setClues(List<Clue> clues) { this.clues = clues; }
}