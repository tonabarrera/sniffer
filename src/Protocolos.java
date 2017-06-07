import org.jnetpcap.PcapIf;;
import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;
import java.util.List;

public class Protocolos extends javax.swing.JFrame {

  /*Declaración de las variable referentes a la capa logica*/
  //Lista para guardar las tramas recibidas con sus respectivos analisis generados en la clase Analisis trama
  private List<AnalisisTrama> analisisTramas;
  //Variable para manipular la trama con la que se trabaja actualmente, permitirá actualizar la UI con datos de esta trama
  private AnalisisTrama tramaActual;

  /*Variables usadas para la conexión a Pcap
  * Estas variables se obtienen mediante los parametros ingresados por el usuario en el frame Launch2*/
  //Interfaz seleccionada
  private PcapIf deviceSelected;
  //Define si la captura de paquetes se realizará mediante una interfaz de red o un archivo
  private boolean isFile;
  //Define si se ignora el campo numPaquetes
  private boolean isInfinite;
  //Timeout seleccionado por el usuario
  private int timeout;
  //Define el numero de paquetes que se desean capturar
  private int numPaquetes;
  //Contiene el filtro especificado por el usuario
  private String filtro;
  //Nombre del archivo a leer para obtener los paquetes, usado solo si isFile=true
  private String nombreArchivo;
  //Variable para capturar cualquier error e imprimirlo como un String
  private StringBuilder errbuf = new StringBuilder();

  /*Variables para conectar la UI con las clases logicas que reciben y analizan paquetes */
  //Instancia con los metodos para conexion, y captura de paquetes
  private CapturaTramas capturador;
  //Variable para controlar el estado de recepcion/pausa de paquetes
  private boolean isReceiving;

  //Contructor con los params recibidos del frame anterior
  public Protocolos(PcapIf deviceSelected, int timeout, int numPaquetes, boolean isFile, boolean isInfinite, String filtro, String nombreArchivo) {

    /*Recibiendo los parametros del frame anterior*/
    analisisTramas =  new LinkedList<>();
    this.deviceSelected =  deviceSelected;
    this.isFile =  isFile;
    this.isInfinite = isInfinite;
    this.filtro =  filtro;
    this.timeout = timeout;
    this.numPaquetes = numPaquetes;
    this.nombreArchivo =  nombreArchivo;
    this.isReceiving = false;

    /*Creando la UI*/
    initComponents();
  }

  /*Seccion para la creación de la UI, esta sección no debe ser modificada salvo casos muy específicos*/
  private void initComponents() {
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jSeparator1 = new javax.swing.JSeparator();
    txtFiltro = new javax.swing.JTextField();
    btnIniciar = new javax.swing.JToggleButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    tablaPaquetes = new javax.swing.JTable();
    jSeparator2 = new javax.swing.JSeparator();
    jLabel4 = new javax.swing.JLabel();
    jScrollPane2 = new javax.swing.JScrollPane();
    listaAnalisis = new javax.swing.JList();
    jLabel5 = new javax.swing.JLabel();
    jScrollPane3 = new javax.swing.JScrollPane();
    listaOriginal = new javax.swing.JList();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    jMenu2 = new javax.swing.JMenu();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jLabel1.setText("ESCOM | IPN");

    jLabel2.setText("Weird Shark");

    jLabel3.setText("Filtro");

    btnIniciar.setText("Iniciar");
    btnIniciar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnIniciarActionPerformed(evt);
      }
    });

    tablaPaquetes.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null,null,null,null}
      },
      new String [] {
        "No.", "Tiempo", "Origen", "Destino","Protocolo","Tamaño","Info"
      }
    ));
    jScrollPane1.setViewportView(tablaPaquetes);

    jLabel4.setText("Análisis");

    listaAnalisis.setModel(new javax.swing.AbstractListModel() {
      String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
      public int getSize() { return strings.length; }
      public Object getElementAt(int i) { return strings[i]; }
    });
    jScrollPane2.setViewportView(listaAnalisis);

    jLabel5.setText("Trama original");

    listaOriginal.setModel(new javax.swing.AbstractListModel() {
      String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
      public int getSize() { return strings.length; }
      public Object getElementAt(int i) { return strings[i]; }
    });
    jScrollPane3.setViewportView(listaOriginal);

    jMenu1.setText("File");
    jMenuBar1.add(jMenu1);

    jMenu2.setText("Edit");
    jMenuBar1.add(jMenu2);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
          .addGap(0, 0, Short.MAX_VALUE)
          .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        .addGroup(layout.createSequentialGroup()
          .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGap(0, 0, Short.MAX_VALUE))
        .addGroup(layout.createSequentialGroup()
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
              .addGap(18, 18, 18)
              .addComponent(jLabel1)
              .addGap(228, 228, 228)
              .addComponent(jLabel2)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(btnIniciar))
            .addGroup(layout.createSequentialGroup()
              .addGap(19, 19, 19)
              .addComponent(jLabel3)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
              .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
              .addContainerGap()
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel4)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel5)
                .addGroup(layout.createSequentialGroup()
                  .addGap(10, 10, 10)
                  .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))))
          .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(jLabel1)
            .addComponent(jLabel2)
            .addComponent(btnIniciar))
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
          .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
              .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addGap(18, 18, 18)
              .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4)
                .addComponent(jLabel5))
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
              .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addContainerGap(26, Short.MAX_VALUE))
    );
    pack();
  }
  /*Fin de la creación de la UI*/

  /*Sección para agregar los Event Listeners correspondientes*/
  //Boton - Iniciar: Comienza o termina la recepción de paquetes
  private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {
    //Iniciando la recepción de paquetes
    if(isReceiving == false){
      //Generando conexión con pcap
      capturador =  new CapturaTramas(deviceSelected.getName(),(64*1024),timeout,filtro);
      capturador.conectarPcap();
      //Capturando los paquetes mediante el uso de un thread independiente para esto
      AdministradorPaquetes administradorPaquetes =  new AdministradorPaquetes();
      administradorPaquetes.start();
      //Realizando un toggle
      isReceiving =  true;
    }else{
      //Pausando la obtención de paquetes, mediante el cierre de la conexión
      capturador.pausarObtenecion();
    }
  }

  /*Declaración de metodos de utilería en la aplicación*/
  //Metodo para agregar una fila a la JTable, la fila se llena con los valores de trama actual
  private void argegarFila(){
    DefaultTableModel modelo = (DefaultTableModel) tablaPaquetes.getModel();
    /*{numero, tiempo, ipOrigen, ipDestino, Protocolo, Tamaño, Info}*/
    String[]datosPaquete =  {String.valueOf(tramaActual.getNumero()),
                              tramaActual.getTiempo(),tramaActual.getIpOrigen(),
                              tramaActual.getIpDestino(),tramaActual.getProtocolo(),
                              String.valueOf(tramaActual.getTamaño()), tramaActual.getInfo()};
    modelo.addRow(datosPaquete);
  }

  /*Clase Administrador de Paquetes
  * Esta clase permitirá tener un thread destinado unicamente a la recepción de paquetes,
  * de esta manera el thread principal del frame no se ve afectado en su performance
  * */
  class AdministradorPaquetes extends Thread{
    public void run(){
      //Validamos la opcion de conexión elegida
      if(!isInfinite){
        //Opción: Número de paquetes
        for(int i = 0; i < numPaquetes; i++){
          tramaActual = capturador.obtenerPaquete();
          tramaActual.setNumero(i);
          tramaActual.analizarPaquete();
          analisisTramas.add(tramaActual);
          //Guardar datos en la tabla
          argegarFila();
          //guardar en la lista
        }
      }else{
        //Opción: Bucle Infinito
        while(isInfinite){

        }
      }
    }//run()
  }//clase Adminsitrador paquetes

  /*Aregando el look and feel nimbus*/
  public static void main(String args[]) {
        /* Set the Nimbus look and feel */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(Protocolos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(Protocolos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(Protocolos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(Protocolos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
  }

  //Variables para UI
  private javax.swing.JToggleButton btnIniciar;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JSeparator jSeparator2;
  private javax.swing.JList listaAnalisis;
  private javax.swing.JList listaOriginal;
  private javax.swing.JTable tablaPaquetes;
  private javax.swing.JTextField txtFiltro;
  // End of variables declaration
}
