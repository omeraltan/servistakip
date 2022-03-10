import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ServisProjeTemelUpdatePage from './servis-proje-temel-update.page-object';

const expect = chai.expect;
export class ServisProjeTemelDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('servisTakipApp.servisProjeTemel.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-servisProjeTemel'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ServisProjeTemelComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('servis-proje-temel-heading'));
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
    await navBarPage.getEntityPage('servis-proje-temel');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateServisProjeTemel() {
    await this.createButton.click();
    return new ServisProjeTemelUpdatePage();
  }

  async deleteServisProjeTemel() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const servisProjeTemelDeleteDialog = new ServisProjeTemelDeleteDialog();
    await waitUntilDisplayed(servisProjeTemelDeleteDialog.deleteModal);
    expect(await servisProjeTemelDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /servisTakipApp.servisProjeTemel.delete.question/
    );
    await servisProjeTemelDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(servisProjeTemelDeleteDialog.deleteModal);

    expect(await isVisible(servisProjeTemelDeleteDialog.deleteModal)).to.be.false;
  }
}
