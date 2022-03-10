import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ProjeTemelUpdatePage from './proje-temel-update.page-object';

const expect = chai.expect;
export class ProjeTemelDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('servisTakipApp.projeTemel.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-projeTemel'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ProjeTemelComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('proje-temel-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('proje-temel');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateProjeTemel() {
    await this.createButton.click();
    return new ProjeTemelUpdatePage();
  }

  async deleteProjeTemel() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const projeTemelDeleteDialog = new ProjeTemelDeleteDialog();
    await waitUntilDisplayed(projeTemelDeleteDialog.deleteModal);
    expect(await projeTemelDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/servisTakipApp.projeTemel.delete.question/);
    await projeTemelDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(projeTemelDeleteDialog.deleteModal);

    expect(await isVisible(projeTemelDeleteDialog.deleteModal)).to.be.false;
  }
}
