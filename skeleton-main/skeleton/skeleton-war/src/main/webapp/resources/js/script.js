function toggleTheme() {
    document.body.classList.add('theme-transition');
    document.body.classList.toggle('dark-theme');
    localStorage.setItem('theme', document.body.classList.contains('dark-theme') ? 'dark' : 'light');

    setTimeout(() => {
        document.body.classList.remove('theme-transition');
    }, 500);
}

function exportarTabelaParaImagem() {
    const tabela = document.getElementById('mainForm:produtoTable');

    if (!tabela) {
        alert('Tabela não encontrada!');
        return;
    }

    html2canvas(tabela).then(canvas => {
        const link = document.createElement('a');
        link.download = 'tabela_equipamentos.png';
        link.href = canvas.toDataURL('image/png');
        link.click(); // dispara o download
    });
}

function exportarTabelaParaPDF() {
    const tabela = document.getElementById('mainForm:produtoTable');
    if (!tabela) return;

    html2canvas(tabela).then(canvas => {
        const imgData = canvas.toDataURL('image/png');
        const { jsPDF } = window.jspdf;
        const pdf = new jsPDF('p', 'mm', 'a4');
        const pageWidth = pdf.internal.pageSize.getWidth();
        const pageHeight = pdf.internal.pageSize.getHeight();
        const imgWidth = pageWidth - 20;
        const imgHeight = canvas.height * imgWidth / canvas.width;

        let position = 10;
        if (imgHeight > pageHeight) {
            pdf.addImage(imgData, 'PNG', 10, position, imgWidth, pageHeight - 20);
        } else {
            pdf.addImage(imgData, 'PNG', 10, position, imgWidth, imgHeight);
        }

        pdf.save('tabela_equipamentos.pdf');
    });
}